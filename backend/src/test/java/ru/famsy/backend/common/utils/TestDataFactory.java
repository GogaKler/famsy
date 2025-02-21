package ru.famsy.backend.common.utils;

import ru.famsy.backend.modules.auth.dto.LoginDTO;
import ru.famsy.backend.modules.auth.dto.RegisterDTO;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user_session.UserSessionEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestDataFactory {

  public static RegisterDTO createRegisterDTO() {
    String uuid = UUID.randomUUID().toString().substring(0, 8);
    RegisterDTO dto = new RegisterDTO();
    dto.setEmail("test" + uuid + "@example.com");
    dto.setUsername("testUser" + uuid);
    dto.setPassword("Password123");
    return dto;
  }


  public static LoginDTO createLoginDTO(String login, String password) {
    LoginDTO dto = new LoginDTO();
    dto.setLogin(login);
    dto.setPassword(password != null ? password : "Password123");
    return dto;
  }

  public static LoginDTO createLoginDTO(String login) {
    return createLoginDTO(login, null);
  }

  public static UserEntity createUserEntity() {
    String uuid = UUID.randomUUID().toString().substring(0, 8);
    UserEntity user = new UserEntity();
    user.setEmail("test" + uuid + "@example.com");
    user.setUsername("testUser" + uuid);
    user.setPassword("hashedPassword");
    return user;
  }

  public static UserSessionEntity createUserSession(UserEntity user) {
    UserSessionEntity session = new UserSessionEntity();
    session.setUser(user);
    session.setSessionId(UUID.randomUUID().toString());
    session.setDeviceFingerprint(UUID.randomUUID().toString());
    session.setLastActivityAt(LocalDateTime.now());
    session.setBrowser("Chrome");
    session.setDeviceType("DESKTOP");
    session.setOs("Windows");
    session.setDeviceModel("PC");
    session.setIp("127.0.0.1");
    session.setCountry("Test Country");
    session.setCity("Test City");
    session.setRegion("Test Region");
    session.setTimezone("UTC");
    return session;
  }
} 