package ru.famsy.backend.integration.modules.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import ru.famsy.backend.common.exception.dto.ErrorResponse;
import ru.famsy.backend.common.utils.TestDataFactory;
import ru.famsy.backend.integration.common.base.BaseIntegrationTest;
import ru.famsy.backend.integration.common.utils.TestIntegrationResponseUtils;
import ru.famsy.backend.modules.auth.dto.LoginDTO;
import ru.famsy.backend.modules.auth.dto.RegisterDTO;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user.UserRepository;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user_session.UserSessionRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Auth Controller Integration Tests")
class AuthControllerIntegrationTest extends BaseIntegrationTest {
  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected UserSessionRepository userSessionRepository;

  private RegisterDTO testRegisterDTO;

  @BeforeEach
  void setup() {
    userSessionRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();
    testRegisterDTO = TestDataFactory.createRegisterDTO();
  }

  @Nested
  @DisplayName("POST /auth/register")
  class Register {
    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    void shouldRegisterNewUser() throws Exception {
      // Действие
      registerAndLoginUser(testRegisterDTO);

      // Проверка
      TestIntegrationResponseUtils.assertHasAuthCookies(requestUtils.getLastResponseCookies());
      UserEntity savedUser = userRepository.findByEmail(testRegisterDTO.getEmail()).orElse(null);

      assertNotNull(savedUser);
      assertEquals(testRegisterDTO.getEmail(), savedUser.getEmail());
      assertEquals(testRegisterDTO.getUsername(), savedUser.getUsername());
      assertEquals(1, userSessionRepository.findAllByUser(savedUser).size());
    }

    @Test
    @DisplayName("Ошибка при регистрации с существующим email")
    void shouldFailWithExistingEmail() throws Exception {
      // Подготовка
      registerAndLoginUser(testRegisterDTO);
      logoutUser();

      // Действие
      MvcResult result = performPost("/auth/register", testRegisterDTO)
              .perform()
              .andExpect(status().isBadRequest())
              .andReturn();

      // Проверка
      ErrorResponse errorResponse = TestIntegrationResponseUtils.parseErrorResponse(result);
      assertNotNull(errorResponse.getMessage());
      assertEquals("Validation exception", errorResponse.getError());
    }

    @Test
    @DisplayName("Ошибка при регистрации с невалидным email")
    void shouldFailWithInvalidEmail() throws Exception {
      // Подготовка
      testRegisterDTO.setEmail("invalid-email");

      // Действие
      MvcResult result = performPost("/auth/register", testRegisterDTO)
              .perform()
              .andExpect(status().isBadRequest())
              .andReturn();

      // Проверка
      TestIntegrationResponseUtils.assertHasFieldError(result, "email");
    }

    @Test
    @DisplayName("Ошибка при попытке регистрации авторизованным пользователем")
    void shouldFailWhenUserAlreadyAuthenticated() throws Exception {
      // Подготовка
      registerAndLoginUser(testRegisterDTO);

      // Действие
      MvcResult result = performPost("/auth/register", testRegisterDTO)
              .perform()
              .andExpect(status().isForbidden())
              .andReturn();

      // Проверка
      ErrorResponse errorResponse = TestIntegrationResponseUtils.parseErrorResponse(result);
      assertNotNull(errorResponse.getMessage());
      assertEquals("Forbidden", errorResponse.getError());
    }

    @Test
    @DisplayName("Ошибка при регистрации с пустым email")
    void shouldFailWithEmptyEmail() throws Exception {
      testRegisterDTO.setEmail("");

      MvcResult result = performPost("/auth/register", testRegisterDTO)
              .perform()
              .andExpect(status().isBadRequest())
              .andReturn();

      // Проверяем только наличие ошибки валидации для поля email
      TestIntegrationResponseUtils.assertHasFieldError(result, "email");
    }

    @Test
    @DisplayName("Ошибка при регистрации с пустым username")
    void shouldFailWithEmptyUsername() throws Exception {
      // Подготовка
      testRegisterDTO.setUsername("");

      // Действие
      MvcResult result = performPost("/auth/register", testRegisterDTO)
              .perform()
              .andExpect(status().isBadRequest())
              .andReturn();

      // Проверка
      TestIntegrationResponseUtils.assertHasFieldError(result, "username");
    }

    @Test
    @DisplayName("Ошибка при регистрации с коротким паролем")
    void shouldFailWithShortPassword() throws Exception {
      // Подготовка
      testRegisterDTO.setPassword("short");

      // Действие
      MvcResult result = performPost("/auth/register", testRegisterDTO)
              .perform()
              .andExpect(status().isBadRequest())
              .andReturn();

      // Проверка
      TestIntegrationResponseUtils.assertHasFieldError(result, "password");
    }

    @Test
    @DisplayName("Ошибка при регистрации с существующим username")
    void shouldFailWithExistingUsername() throws Exception {
      // Подготовка
      registerAndLoginUser(testRegisterDTO);
      logoutUser();

      // Создаем второго пользователя с тем же username, но другим email
      testRegisterDTO.setEmail("another" + testRegisterDTO.getEmail());

      MvcResult result = performPost("/auth/register", testRegisterDTO)
              .perform()
              .andExpect(status().isBadRequest())
              .andReturn();

      ErrorResponse errorResponse = TestIntegrationResponseUtils.parseErrorResponse(result);
      assertNotNull(errorResponse.getMessage());
      assertEquals("Validation exception", errorResponse.getError());
    }

    @Test
    @DisplayName("Ошибка при регистрации с невалидным username")
    void shouldFailWithInvalidUsername() throws Exception {
      // Подготовка (со спец символом)
      testRegisterDTO.setUsername("user@name");

      // Действие
      MvcResult result = performPost("/auth/register", testRegisterDTO)
              .perform()
              .andExpect(status().isBadRequest())
              .andReturn();

      // Проверка
      TestIntegrationResponseUtils.assertHasFieldError(result, "username");
    }
  }

  @Nested
  @DisplayName("POST /auth/login")
  class Login {
    @Test
    @DisplayName("Успешная авторизация по email")
    void shouldLoginWithEmail() throws Exception {
      // Подготовка
      registerAndLoginUser(testRegisterDTO);
      logoutUser();

      // Действие
      LoginDTO loginDTO = TestDataFactory.createLoginDTO(testRegisterDTO.getUsername(), testRegisterDTO.getPassword());
      MvcResult result = loginUser(loginDTO);

      // Проверка
      UserDTO responseUser = TestIntegrationResponseUtils.parseResponse(result, UserDTO.class);
      TestIntegrationResponseUtils.assertHasAuthCookies(requestUtils.getLastResponseCookies());
      assertEquals(testRegisterDTO.getEmail(), responseUser.getEmail());
    }

    @Test
    @DisplayName("Успешная авторизация по username")
    void shouldLoginWithUsername() throws Exception {
      // Подготовка
      registerAndLoginUser(testRegisterDTO);
      logoutUser();

      // Действие
      LoginDTO loginDTO = TestDataFactory.createLoginDTO(testRegisterDTO.getUsername(), testRegisterDTO.getPassword());
      MvcResult result = loginUser(loginDTO);

      // Проверка
      UserDTO responseUser = TestIntegrationResponseUtils.parseResponse(result, UserDTO.class);
      TestIntegrationResponseUtils.assertHasAuthCookies(requestUtils.getLastResponseCookies());
      assertEquals(testRegisterDTO.getUsername(), responseUser.getUsername());
    }

    @Test
    @DisplayName("Ошибка при неверном пароле")
    void shouldFailWithWrongPassword() throws Exception {
      // Подготовка
      registerAndLoginUser(testRegisterDTO);
      logoutUser();

      // Действие
      LoginDTO loginDTO = TestDataFactory.createLoginDTO(testRegisterDTO.getUsername(),"wrongPassword123");
      MvcResult result = performPost("/auth/login", loginDTO)
              .perform()
              .andExpect(status().isBadRequest())
              .andReturn();

      // Проверка
      TestIntegrationResponseUtils.assertCookiesCleared(requestUtils.getLastResponseCookies());
      ErrorResponse errorResponse = TestIntegrationResponseUtils.parseErrorResponse(result);
      assertNotNull(errorResponse.getMessage());
      assertEquals("Validation exception", errorResponse.getError());
    }
  }

  @Nested
  @DisplayName("POST /auth/logout")
  class Logout {
    @Test
    @DisplayName("Успешный выход из системы")
    void shouldLogoutSuccessfully() throws Exception {
      // Подготовка
      registerAndLoginUser(testRegisterDTO);
      UserEntity user = userRepository.findByEmail(testRegisterDTO.getEmail()).orElseThrow();
      assertEquals(1, userSessionRepository.findAllByUser(user).size());

      // Действие
      logoutUser();

      // Проверка
      assertEquals(0, userSessionRepository.findAllByUser(user).size());
    }

    @Test
    @DisplayName("Выход без авторизации")
    void shouldAllowLogoutWhenNotAuthenticated() throws Exception {
      // Действие
      logoutUser();
    }
  }
}