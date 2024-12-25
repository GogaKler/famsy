package ru.famsy.backend.modules.auth;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.famsy.backend.modules.auth.dto.AuthServiceResult;
import ru.famsy.backend.modules.auth.dto.LoginDTO;
import ru.famsy.backend.modules.auth.dto.RegisterDTO;
import ru.famsy.backend.modules.auth.dto.TokenPairDTO;
import ru.famsy.backend.modules.auth.exception.AuthValidationException;
import ru.famsy.backend.modules.auth.provider.JwtTokenProvider;
import ru.famsy.backend.modules.device.DeviceInfo;
import ru.famsy.backend.modules.refresh_token.RefreshTokenEntity;
import ru.famsy.backend.modules.refresh_token.RefreshTokenRepository;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  AuthService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository) {
    this.userRepository = userRepository;
    this.jwtTokenProvider = jwtTokenProvider;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  public AuthServiceResult register(RegisterDTO registerDTO, DeviceInfo deviceInfo) {
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

    TokenPairDTO tokenPairDTO = jwtTokenProvider.generateTokenPair(userEntity, deviceInfo.deviceId());

    return new AuthServiceResult(userEntity, tokenPairDTO);
  }

  public AuthServiceResult login(@NotNull LoginDTO loginDTO, DeviceInfo deviceInfo) {
    Optional<UserEntity> userEntityOptional = this.userRepository.findByEmail(loginDTO.getLogin())
            .or(() -> this.userRepository.findByUsername(loginDTO.getLogin()));

    if (userEntityOptional.isEmpty()) {
      throw new AuthValidationException("Неверный логин или пароль");
    }

    UserEntity userEntity = userEntityOptional.get();

    if (!passwordEncoder.matches(loginDTO.getPassword(), userEntity.getPassword())) {
      throw new AuthValidationException("Неверный логин или пароль");
    }

    // TODO: Сломается при логине с одного устройства, но с разных браузеров. Добавить поддержку браузера в DeviceService
    Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenRepository.findByDeviceIdAndUser(deviceInfo.deviceId(), userEntity);

    if (refreshTokenEntity.isPresent()) {
      UserEntity userEntityFromToken = refreshTokenEntity.get().getUser();
      if (userEntityFromToken.getId().equals(userEntity.getId())) {
        refreshTokenRepository.delete(refreshTokenEntity.get());
      }
    }

    TokenPairDTO tokenPairDTO = jwtTokenProvider.generateTokenPair(userEntity, deviceInfo.deviceId());
    return new AuthServiceResult(userEntity, tokenPairDTO);
  }

  @Transactional
  public void revokeRefreshToken(String refreshToken) {
    refreshTokenRepository.deleteByToken(refreshToken);
  }

  public boolean isAlreadyUserAuth() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void cleanupExpiredTokens() {
    refreshTokenRepository.deleteByExpiryDateTimeBefore(LocalDateTime.now());
  }
}
