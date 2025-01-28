package ru.famsy.backend.integration.modules.jwt;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import ru.famsy.backend.common.config.SecurityConfigProperties;
import ru.famsy.backend.common.exception.dto.ErrorResponse;
import ru.famsy.backend.common.utils.TestDataFactory;
import ru.famsy.backend.integration.common.base.BaseIntegrationTest;
import ru.famsy.backend.integration.common.utils.TestIntegrationResponseUtils;
import ru.famsy.backend.modules.auth.dto.LoginDTO;
import ru.famsy.backend.modules.auth.dto.RegisterDTO;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user.UserRepository;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user_session.UserSessionEntity;
import ru.famsy.backend.modules.user_session.UserSessionRepository;
import ru.famsy.backend.modules.user_session.dto.UserSessionDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("JWT Flow Integration Tests")
class JwtFlowIntegrationTest extends BaseIntegrationTest {
  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected UserSessionRepository userSessionRepository;

  private RegisterDTO testRegisterDTO;
  private UserEntity authenticatedUser;

  @BeforeEach
  void setup() throws Exception {
    userSessionRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();
    testRegisterDTO = TestDataFactory.createRegisterDTO();
    authenticatedUser = createAndAuthenticateTestUser();
  }

  private UserEntity createAndAuthenticateTestUser() throws Exception {
    MvcResult result = registerAndLoginUser(testRegisterDTO);
    UserDTO userDTO = TestIntegrationResponseUtils.parseResponse(result, UserDTO.class);
    return userRepository.findByEmail(userDTO.getEmail()).orElseThrow();
  }

  @Nested
  @DisplayName("Access Token Tests")
  class AccessTokenTests {
    @Test
    @DisplayName("Успешный доступ к защищенному ресурсу с валидным access token")
    void shouldAccessProtectedResourceWithValidToken() throws Exception {
      // Действие
      MvcResult result = performGet("/sessions")
              .perform()
              .andExpect(status().isOk())
              .andReturn();

      // Проверка
      assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Ошибка доступа к защищенному ресурсу без токена")
    void shouldFailToAccessProtectedResourceWithoutToken() throws Exception {
      // Подготовка
      logoutUser();

      // Действие и проверка
      performGet("/sessions")
              .perform()
              .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Ошибка доступа к защищенному ресурсу с невалидным токеном")
    void shouldFailToAccessProtectedResourceWithInvalidToken() throws Exception {
      // Подготовка
      logoutUser();
      Cookie invalidCookie = new Cookie("access_token", "invalid.token.here");
      requestUtils.setLastResponseCookies(new Cookie[]{invalidCookie});

      // Действие и проверка
      MvcResult result = performGet("/sessions")
              .perform()
              .andExpect(status().isUnauthorized())
              .andReturn();

      // Проверка сообщения об ошибке
      ErrorResponse errorResponse = TestIntegrationResponseUtils.parseErrorResponse(result);
      assertNotNull(errorResponse.getMessage());
      assertEquals("Unauthorized", errorResponse.getError());
    }
  }

  @Nested
  @DisplayName("Refresh Token Tests")
  class RefreshTokenTests {
    @Test
    @DisplayName("Успешное обновление токенов")
    void shouldRefreshTokensSuccessfully() throws Exception {
      // Подготовка: ждем, пока access token истечет
      awaitWhenAccessTokenExpired();

      Cookie[] oldCookies = requestUtils.getLastResponseCookies();

      // Действие
      MvcResult result = performGet("/sessions")
              .perform()
              .andExpect(status().isOk())
              .andReturn();

      // Проверка
      Cookie[] newCookies = requestUtils.getLastResponseCookies();
      TestIntegrationResponseUtils.assertHasAuthCookies(newCookies);
      assertNotEquals(oldCookies[0].getValue(), newCookies[0].getValue());
      assertNotEquals(oldCookies[1].getValue(), newCookies[1].getValue());
    }

    @Test
    @DisplayName("Ошибка при попытке обновления с невалидным refresh token")
    void shouldFailToRefreshWithInvalidRefreshToken() throws Exception {
      // Подготовка
      awaitWhenAccessTokenExpired();
      Cookie[] currentCookies = requestUtils.getLastResponseCookies();
      Cookie invalidRefreshToken = new Cookie("refresh_token", "invalid.token");
      requestUtils.setLastResponseCookies(new Cookie[]{currentCookies[0], invalidRefreshToken});

      // Действие и проверка
      MvcResult result = performGet("/sessions")
              .perform()
              .andExpect(status().isUnauthorized())
              .andReturn();

      // Проверка очистки куков
      TestIntegrationResponseUtils.assertCookiesCleared(result.getResponse().getCookies());
    }

    @Test
    @DisplayName("Ошибка при попытке обновления с пустым refresh token")
    void shouldFailToRefreshWithEmptyRefreshToken() throws Exception {
      // Подготовка
      awaitWhenAccessTokenExpired();
      Cookie[] currentCookies = requestUtils.getLastResponseCookies();
      requestUtils.setLastResponseCookies(new Cookie[]{currentCookies[0]});

      // Действие и проверка
      MvcResult result = performGet("/sessions")
              .perform()
              .andExpect(status().isUnauthorized())
              .andReturn();

      // Проверка очистки куков
      TestIntegrationResponseUtils.assertCookiesCleared(result.getResponse().getCookies());
    }


    @Test
    @DisplayName("Ошибка при одновременном использовании старой и новой пары токенов")
    void shouldFailWithConcurrentTokenUsage() throws Exception {
        // Подготовка
        Cookie[] oldCookies = requestUtils.getLastResponseCookies();
        
        // Действие
        awaitWhenAccessTokenExpired();
        performGet("/sessions")
                .perform()
                .andExpect(status().isOk())
                .andReturn();
                
        Cookie[] newTokens = requestUtils.getLastResponseCookies();
        
        // Проверка
        requestUtils.setLastResponseCookies(oldCookies);
        performGet("/sessions")
                .perform()
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Ошибка при повторном использовании refresh token")
    void shouldFailWithReusedRefreshToken() throws Exception {
      // Подготовка
      Cookie[] oldCookies = requestUtils.getLastResponseCookies();

      // Действие
      awaitWhenAccessTokenExpired();

      performGet("/sessions")
              .perform()
              .andExpect(status().isOk());

      Cookie[] newCookies = requestUtils.getLastResponseCookies();

      // Проверка
      requestUtils.setLastResponseCookies(new Cookie[]{newCookies[0], oldCookies[1]});

      awaitWhenAccessTokenExpired();

      performGet("/sessions")
              .perform()
              .andExpect(status().isUnauthorized());
    }
  }

  @Nested
  @DisplayName("Token Tampering Tests")
  // TODO: Написать тесты, которые покроют манипуляцию токенами
  class TokenTamperingTests {
    @Test
    @DisplayName("Ошибка при попытке использовать refresh token от другой сессии")
    void shouldFailWithMixedSessionTokens() throws Exception {
      // Подготовка
      Cookie[] otherCookies = requestUtils.getLastResponseCookies();
      clearAuthCookies();

      LoginDTO loginDTO = new LoginDTO();
      loginDTO.setLogin(testRegisterDTO.getEmail());
      loginDTO.setPassword(testRegisterDTO.getPassword());

      MvcResult secondSession = performPost("/auth/login", loginDTO)
              .withUserAgent("Another Browser")
              .perform()
              .andReturn();

      // Действие
      Cookie accessToken = secondSession.getResponse().getCookie("access_token");
      Cookie refreshToken = otherCookies[1];

      awaitWhenAccessTokenExpired();

      requestUtils.setLastResponseCookies(new Cookie[]{accessToken, refreshToken});

      // Проверка
      performGet("/sessions")
              .perform()
              .andExpect(status().isUnauthorized());
    }
  }

  @Nested
  @DisplayName("Session Management Tests")
  class SessionManagementTests {
    @Test
    @DisplayName("Успешное создание новой сессии при входе с нового устройства")
    void shouldCreateNewSessionForNewDevice() throws Exception {
      // Подготовка
      List<UserSessionDTO> sessionsBefore = TestIntegrationResponseUtils.parseResponseList(
              performGet("/sessions")
                      .perform()
                      .andReturn(),
              UserSessionDTO.class
      );

      clearAuthCookies();

      // Действие
      LoginDTO loginDTO = new LoginDTO();
      loginDTO.setLogin(testRegisterDTO.getEmail());
      loginDTO.setPassword(testRegisterDTO.getPassword());

      performPost("/auth/login", loginDTO)
              .withUserAgent("Different Browser")
              .perform()
              .andExpect(status().isOk());

      // Проверка
      MvcResult result = performGet("/sessions")
              .withUserAgent("Different Browser")
              .perform()
              .andExpect(status().isOk())
              .andReturn();

      List<UserSessionDTO> sessionsAfter = TestIntegrationResponseUtils.parseResponseList(result, UserSessionDTO.class);
      assertEquals(sessionsBefore.size() + 1, sessionsAfter.size());
    }

    @Test
    @DisplayName("Удаление старой сессии при превышении лимита")
    void shouldDeleteOldestSessionWhenLimitExceeded() throws Exception {
      // Подготовка: создаем максимальное количество сессий
      for (int sessionNum = 0; sessionNum < SecurityConfigProperties.MAX_SESSIONS_PER_USER; sessionNum++) {
        clearAuthCookies();

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(testRegisterDTO.getEmail());
        loginDTO.setPassword(testRegisterDTO.getPassword());

        performPost("/auth/login", loginDTO)
                .withUserAgent("Browser " + sessionNum)
                .perform()
                .andExpect(status().isOk());
      }

      // Проверяем текущее количество сессий
      MvcResult result = performGet("/sessions")
              .perform()
              .andExpect(status().isOk())
              .andReturn();

      List<UserSessionDTO> sessions = TestIntegrationResponseUtils.parseResponseList(result, UserSessionDTO.class);
      assertEquals(5, sessions.size()); // Максимальное количество сессий
    }
  }

  @Nested
  @DisplayName("Logout Tests")
  class LogoutTests {
    @Test
    @DisplayName("После logout невозможно использовать старые токены")
    void shouldNotAllowUsingOldTokensAfterLogout() throws Exception {
      // Подготовка
      Cookie[] oldCookies = requestUtils.getLastResponseCookies();

      MvcResult sessionsResult = performGet("/sessions")
              .perform()
              .andReturn();

      List<UserSessionDTO> sessionsBefore = TestIntegrationResponseUtils.parseResponseList(
              sessionsResult,
              UserSessionDTO.class
      );

      // Действие
      logoutUser();

      // Проверка
      requestUtils.setLastResponseCookies(oldCookies);
      performGet("/sessions")
              .perform()
              .andExpect(status().isUnauthorized());
      List<UserSessionEntity> sessionsAfter = userSessionRepository.findAllByUser(authenticatedUser);
      assertEquals(sessionsBefore.size() - 1, sessionsAfter.size());
    }

    @Test
    @DisplayName("После очистки cookies без logout сессия остается активной")
    void shouldKeepSessionActiveAfterCookieClear() throws Exception {
      // Подготовка
      List<UserSessionDTO> sessionsBefore = TestIntegrationResponseUtils.parseResponseList(
              performGet("/sessions")
                      .perform()
                      .andReturn(),
              UserSessionDTO.class
      );

      // Действие
      clearAuthCookies();

      // Проверка
      performGet("/sessions")
              .perform()
              .andExpect(status().isUnauthorized());
      List<UserSessionEntity> sessionsAfter = userSessionRepository.findAllByUser(authenticatedUser);
      assertEquals(sessionsBefore.size(), sessionsAfter.size());
    }

    @Test
    @DisplayName("После logout cookies очищаются корректно")
    void shouldClearCookiesAfterLogout() throws Exception {
      // Подготовка
      TestIntegrationResponseUtils.assertHasAuthCookies(requestUtils.getLastResponseCookies());

      // Действие
      logoutUser();

      // Проверка
      TestIntegrationResponseUtils.assertCookiesCleared(requestUtils.getLastResponseCookies());
    }

    @Test
    @DisplayName("Logout без авторизации не вызывает ошибок")
    void shouldHandleLogoutWhenNotAuthenticated() throws Exception {
      // Подготовка
      logoutUser();

      // Действие и проверка
      logoutUser();
    }
  }
} 