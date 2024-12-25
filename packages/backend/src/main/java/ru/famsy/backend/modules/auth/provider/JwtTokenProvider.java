package ru.famsy.backend.modules.auth.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import ru.famsy.backend.modules.auth.config.SecurityConstants;
import ru.famsy.backend.modules.auth.dto.TokenPairDTO;
import ru.famsy.backend.modules.refresh_token.RefreshTokenEntity;
import ru.famsy.backend.modules.refresh_token.RefreshTokenRepository;
import ru.famsy.backend.modules.user.UserEntity;

import java.security.Key;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
  private final RefreshTokenRepository refreshTokenRepository;
  private final SecureRandom secureRandom = new SecureRandom();
  private final Key key = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes());

  JwtTokenProvider(RefreshTokenRepository refreshTokenRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
  }

  public TokenPairDTO generateTokenPair(UserEntity user, String deviceId) {
    String accessToken = generateAccessToken(user.getUsername());
    String refreshToken = generateRefreshToken();

    RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
    refreshTokenEntity.setToken(refreshToken);
    refreshTokenEntity.setUser(user);
    refreshTokenEntity.setDeviceId(deviceId);
    refreshTokenEntity.setExpiryDateTime(LocalDateTime.now()
            .plusSeconds(SecurityConstants.REFRESH_TOKEN_EXPIRATION / 1000));

    // TODO: Убрать сохранение токена в провайдере. Вынести в сервисы
    refreshTokenRepository.save(refreshTokenEntity);

    return new TokenPairDTO(accessToken, refreshToken);
  }

  public String generateAccessToken(String username) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + SecurityConstants.ACCESS_TOKEN_EXPIRATION);

    return Jwts.builder()
            .subject(username)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact();
  }

  private String generateRefreshToken() {
    byte[] randomBytes = new byte[64];
    secureRandom.nextBytes(randomBytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
  }

  public String getUsernameFromToken(String token) {
    Jws<Claims> jwsClaims = Jwts.parser()
            .setSigningKey(key)
            .build()
            .parseSignedClaims(token);

    return jwsClaims.getBody().getSubject();
  }
}
