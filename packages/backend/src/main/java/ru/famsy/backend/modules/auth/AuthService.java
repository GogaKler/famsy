package ru.famsy.backend.modules.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.famsy.backend.modules.auth.dto.LoginDTO;
import ru.famsy.backend.modules.auth.dto.RegisterDTO;
import ru.famsy.backend.modules.auth.dto.TokenPairDTO;
import ru.famsy.backend.modules.auth.exception.AuthAlreadyException;
import ru.famsy.backend.modules.auth.exception.AuthValidationException;
import ru.famsy.backend.modules.jwt.JwtCookieService;
import ru.famsy.backend.modules.jwt.JwtTokenService;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user.UserRepository;
import ru.famsy.backend.modules.user_session.UserSessionEntity;
import ru.famsy.backend.modules.user_session.UserSessionService;

import java.util.Optional;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private final UserSessionService userSessionService;
  private final JwtTokenService jwtTokenService;
  private final JwtCookieService jwtCookieService;

  AuthService(
          UserRepository userRepository,
          UserSessionService userSessionService,
          JwtTokenService jwtTokenService,
          JwtCookieService jwtCookieService) {
    this.userRepository = userRepository;
    this.userSessionService = userSessionService;
    this.jwtTokenService = jwtTokenService;
    this.jwtCookieService = jwtCookieService;
  }

  @Transactional
  public UserEntity register(RegisterDTO registerDTO, HttpServletRequest request, HttpServletResponse response) {
    if (isAlreadyUserAuth()) {
      throw new AuthAlreadyException();
    }

    if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
      throw new AuthValidationException("Пользователь с таким email уже существует");
    }

    if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
      throw new AuthValidationException("Пользователь с таким username уже существует");
    }

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(registerDTO.getUsername());
    userEntity.setEmail(registerDTO.getEmail());
    userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

    userRepository.save(userEntity);

    UserSessionEntity userSession = userSessionService.createOrUpdateSession(userEntity, request);

    TokenPairDTO tokenPairDTO = jwtTokenService.generateTokenPair(userSession);

    jwtCookieService.addTokenCookies(tokenPairDTO, response);

    return userEntity;
  }

  public UserEntity login(@NotNull LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
    if (isAlreadyUserAuth()) {
      throw new AuthAlreadyException();
    }

    Optional<UserEntity> userEntityOptional = this.userRepository.findByEmail(loginDTO.getLogin())
            .or(() -> this.userRepository.findByUsername(loginDTO.getLogin()));

    if (userEntityOptional.isEmpty()) {
      throw new AuthValidationException("Неверный логин или пароль");
    }

    UserEntity userEntity = userEntityOptional.get();

    if (!passwordEncoder.matches(loginDTO.getPassword(), userEntity.getPassword())) {
      throw new AuthValidationException("Неверный логин или пароль");
    }

    UserSessionEntity userSession = userSessionService.createOrUpdateSession(userEntity, request);

    TokenPairDTO tokenPairDTO = jwtTokenService.generateTokenPair(userSession);

    jwtCookieService.addTokenCookies(tokenPairDTO, response);

    return userEntity;
  }

  @Transactional
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    try {
      String refreshToken = jwtCookieService.extractTokenFromCookie(request, "refresh_token");
      if (refreshToken != null && !refreshToken.isEmpty()) {
        String sessionId = jwtTokenService.extractSessionIdFromToken(refreshToken);
        userSessionService.deleteUserSessionBySessionId(sessionId);
      }

      // TODO: Обработать catch для логирования
    } finally {
      jwtCookieService.clearTokenCookies(response);
      SecurityContextHolder.clearContext();
    }
  }

  public boolean isAlreadyUserAuth() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
  }
}
