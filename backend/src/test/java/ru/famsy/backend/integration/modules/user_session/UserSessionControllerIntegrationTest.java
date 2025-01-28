package ru.famsy.backend.integration.modules.user_session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import ru.famsy.backend.common.utils.TestDataFactory;
import ru.famsy.backend.integration.common.base.BaseIntegrationTest;
import ru.famsy.backend.integration.common.utils.TestIntegrationResponseUtils;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user.UserRepository;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user_session.UserSessionEntity;
import ru.famsy.backend.modules.user_session.UserSessionRepository;
import ru.famsy.backend.modules.user_session.dto.UserSessionDTO;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("User Session Controller Integration Tests")
class UserSessionControllerIntegrationTest extends BaseIntegrationTest {
  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected UserSessionRepository userSessionRepository;

  private UserEntity authenticatedUser;

  @BeforeEach
  void setup() throws Exception {
    userSessionRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();
    authenticatedUser = createAndAuthenticateTestUser();
  }

  private UserEntity createAndAuthenticateTestUser() throws Exception {
    MvcResult result = registerAndLoginUser(TestDataFactory.createRegisterDTO());
    UserDTO userDTO = TestIntegrationResponseUtils.parseResponse(result, UserDTO.class);
    return userRepository.findByEmail(userDTO.getEmail()).orElseThrow();
  }

  private UserEntity switchAuthenticatedTestUser() throws Exception {
    clearAuthCookies();
    return createAndAuthenticateTestUser();
  }

  @Nested
  @DisplayName("GET /sessions")
  class GetUserSessions {
    @Test
    @DisplayName("Успешное получение списка сессий пользователя")
    void shouldGetUserSessions() throws Exception {
      // Действие
      MvcResult result = performGet("/sessions")
              .perform()
              .andExpect(status().isOk())
              .andReturn();

      // Проверка
      List<UserSessionDTO> sessions = TestIntegrationResponseUtils.parseResponseList(result, UserSessionDTO.class);
      assertNotNull(sessions);
      assertFalse(sessions.isEmpty());
      assertEquals(1, sessions.size());

      UserSessionDTO session = sessions.getFirst();
      assertNotNull(session.getId());
      assertNotNull(session.getDeviceFingerprint());
      assertNotNull(session.getLastActivityAt());
      assertNotNull(session.getBrowser());
      assertNotNull(session.getDeviceType());
      assertNotNull(session.getOs());
      assertNotNull(session.getIp());
    }

    @Test
    @DisplayName("Ошибка при получении сессий неавторизованным пользователем")
    void shouldFailWhenUserNotAuthenticated() throws Exception {
      // Подготовка
      logoutUser();

      // Действие и проверка
      performGet("/sessions")
              .perform()
              .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Проверка удаления несуществующей сессии")
    void shouldHandleNonExistentSessionDeletion() throws Exception {
      performDelete("/sessions/99999999")
              .perform()
              .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Проверка удаления сессии с невалидным ID")
    void shouldHandleInvalidSessionId() throws Exception {
      performDelete("/sessions/invalid-id")
              .perform()
              .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Проверка получения сессий после удаления всех сессий")
    void shouldReturnEmptyListAfterDeletingAllSessions() throws Exception {
      // Подготовка
      performDelete("/sessions")
              .perform()
              .andExpect(status().isNoContent());

      // Действие
      MvcResult result = performGet("/sessions")
              .perform()
              .andExpect(status().isOk())
              .andReturn();

      // Проверка
      List<UserSessionDTO> sessions = TestIntegrationResponseUtils.parseResponseList(result, UserSessionDTO.class);
      assertEquals(1, sessions.size());
    }
  }

  @Nested
  @DisplayName("DELETE /sessions/{id}")
  class TerminateSession {
    @Test
    @DisplayName("Успешное завершение конкретной сессии")
    void shouldTerminateSpecificSession() throws Exception {
      // Подготовка
      List<UserSessionEntity> sessions = userSessionRepository.findAllByUser(authenticatedUser);
      assertFalse(sessions.isEmpty());
      UserSessionEntity session = sessions.getFirst();

      // Действие
      performDelete("/sessions/" + session.getId())
              .perform()
              .andExpect(status().isNoContent());

      // Проверка
      assertTrue(userSessionRepository.findAllByUser(authenticatedUser).isEmpty());
    }

    @Test
    @DisplayName("Ошибка при попытке завершить несуществующую сессию")
    void shouldFailWithNonExistentSession() throws Exception {
      performDelete("/sessions/999999")
              .perform()
              .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Ошибка при попытке завершить чужую сессию")
    // TODO: Тест не работает. Дело в разлогине пользователя и одновременной удалении сессии.
    void shouldFailWithOtherUserSession() throws Exception {
      // Подготовка

      // Копируем старого пользователя
      UserEntity otherAuthenticatedUser = authenticatedUser;
      // Логиним нового
      authenticatedUser = switchAuthenticatedTestUser();

      // Ищем сессию старого пользователя
      UserEntity otherUserEntity = userRepository.findByEmail(otherAuthenticatedUser.getEmail()).orElseThrow();
      List<UserSessionEntity> otherSessions = userSessionRepository.findAllByUser(otherUserEntity);
      assertFalse(otherSessions.isEmpty());

      // Действие и проверка
      performDelete("/sessions/" + otherSessions.getFirst().getId())
              .perform()
              .andExpect(status().isForbidden());
    }
  }

  @Nested
  @DisplayName("DELETE /sessions")
  class TerminateAllSessions {
    @Test
    @DisplayName("Успешное завершение всех сессий кроме текущей")
    void shouldTerminateAllOtherSessions() throws Exception {
      // Подготовка
      String deviceFingerprint = "test-device-fingerprint";
      for (int i = 0; i < 2; i++) {
        UserSessionEntity session = new UserSessionEntity();
        session.setUser(authenticatedUser);
        session.setSessionId(UUID.randomUUID().toString());
        session.setDeviceFingerprint(deviceFingerprint + i);
        session.setLastActivityAt(LocalDateTime.now());
        session.setBrowser("Test Browser");
        session.setDeviceType("Test Device");
        session.setOs("Test OS");
        session.setIp("127.0.0.1");
        userSessionRepository.save(session);
      }
      assertEquals(3, userSessionRepository.findAllByUser(authenticatedUser).size());

      // Действие
      performDelete("/sessions")
              .perform()
              .andExpect(status().isNoContent());

      // Проверка
      List<UserSessionEntity> remainingSessions = userSessionRepository.findAllByUser(authenticatedUser);
      assertEquals(1, remainingSessions.size());
    }

    @Test
    @DisplayName("Ошибка при попытке завершить все сессии неавторизованным пользователем")
    void shouldFailWhenUserNotAuthenticated() throws Exception {
      // Подготовка
      logoutUser();

      // Действие и проверка
      performDelete("/sessions")
              .perform()
              .andExpect(status().isUnauthorized());
    }
  }
} 