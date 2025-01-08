package ru.famsy.backend.modules.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.famsy.backend.common.config.SecurityConfigProperties;
import ru.famsy.backend.modules.auth.dto.TokenPairDTO;
import ru.famsy.backend.modules.jwt.exception.TokenExpiredException;
import ru.famsy.backend.modules.jwt.exception.TokenMalformedException;
import ru.famsy.backend.modules.jwt.exception.TokenSignatureException;
import ru.famsy.backend.modules.jwt.exception.TokenValidationException;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user_session.UserSessionEntity;
import ru.famsy.backend.modules.user_session.UserSessionRepository;
import ru.famsy.backend.modules.user_session.UserSessionService;
import ru.famsy.backend.modules.user_session.exception.SessionExpiredException;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class JwtTokenService {
  private Key key;
  private final UserSessionService userSessionService;
  private final UserSessionRepository userSessionRepository;
  private final SecurityConfigProperties securityConfigProperties;

  public JwtTokenService(
          UserSessionService userSessionService,
          UserSessionRepository userSessionRepository,
          SecurityConfigProperties securityConfigProperties
  ) {
    this.userSessionService = userSessionService;
    this.userSessionRepository = userSessionRepository;
    this.securityConfigProperties = securityConfigProperties;
  }

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(securityConfigProperties.getJwtSecret().getBytes());
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void deleteExpiredUserSessions() {
    LocalDateTime expirationThreshold = LocalDateTime.now().minus(securityConfigProperties.getSessionExpiration());
    List<UserSessionEntity> expiredSessions = userSessionRepository.findAllByLastActivityAtBefore(expirationThreshold);
    userSessionRepository.deleteAll(expiredSessions);
  }

  public String generateAccessToken(UserSessionEntity userSession) {
    UserEntity user = userSession.getUser();
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + securityConfigProperties.getAccessTokenExpiration().toMillis());

    return Jwts.builder()
            .subject(user.getId().toString())
            .claim("sessionId", userSession.getSessionId())
            .claim("lastActivityAt", userSession.getLastActivityAt().toString())
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact();
  }

  public String generateRefreshToken(UserSessionEntity userSession) {
    UserEntity user = userSession.getUser();
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + securityConfigProperties.getRefreshTokenExpiration().toMillis());

    return Jwts.builder()
            .subject(user.getId().toString())
            .claim("sessionId", userSession.getSessionId())
            .claim("lastActivityAt", userSession.getLastActivityAt().toString())
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact();
  }

  public TokenPairDTO generateTokenPair(UserSessionEntity userSession) {
    return new TokenPairDTO(generateAccessToken(userSession), generateRefreshToken(userSession));
  }

  public String extractSessionIdFromToken(String token) {
    Claims claims = getTokenPayload(token);
    return claims.get("sessionId", String.class);
  }

  public TokenPairDTO refreshTokens(String refreshToken, HttpServletRequest request) {
    Claims claims = validateRefreshToken(refreshToken, request);
    String sessionId = claims.get("sessionId", String.class);
    UserSessionEntity userSession = userSessionService.getUserSessionBySessionId(sessionId);

    userSession.setLastActivityAt(LocalDateTime.now());
    userSessionRepository.save(userSession);

    return generateTokenPair(userSession);
  }

  public Claims validateAccessToken(String token) {
    try {
      Claims claims = validateToken(token);
      String sessionId = claims.get("sessionId", String.class);
      userSessionService.getUserSessionBySessionId(sessionId);
      return claims;
    } catch (ExpiredJwtException e) {
      throw new TokenExpiredException();
    }
  }

  private Claims validateRefreshToken(String token, HttpServletRequest request) {
    Claims claims = validateToken(token);
    String sessionId = claims.get("sessionId", String.class);
    LocalDateTime lastActivityAt = LocalDateTime.parse(claims.get("lastActivityAt", String.class));

    UserSessionEntity userSession = userSessionService.getUserSessionBySessionId(sessionId);

    if (userSession.getLastActivityAt().plus(securityConfigProperties.getSessionExpiration()).isBefore(LocalDateTime.now())) {
      userSessionService.deleteUserSessionBySessionId(sessionId);
      throw new SessionExpiredException("Сессия истекла");
    }

    if (!userSession.getLastActivityAt().equals(lastActivityAt)) {
      throw new SessionExpiredException("Сессия используется другим пользователем");
    }

//      String deviceFingerprint = userSessionService.generateDeviceFingerprint(request);
//
//      if (!userSession.getDeviceFingerprint().equals(deviceFingerprint)) {
//        throw new SessionExpiredException("Сессия была создана другим пользователем");
//      }

    return claims;
  }

  private Claims validateToken(String token) {
    Claims claims = getTokenPayload(token);
    String sessionId = claims.get("sessionId", String.class);
    String lastActivityAt = claims.get("lastActivityAt", String.class);

    if (sessionId == null || lastActivityAt == null) {
      throw new TokenValidationException("Не действительный токен");
    }

    return claims;
  }

  public Claims getTokenPayload(String token) {
    try {
      return Jwts.parser()
              .setSigningKey(key)
              .build()
              .parseSignedClaims(token)
              .getPayload();
    } catch (ExpiredJwtException e) {
      throw new TokenExpiredException();
    } catch (SecurityException e) {
      throw new TokenSignatureException();
    } catch (MalformedJwtException e) {
      throw new TokenMalformedException();
    } catch (Exception e) {
      throw new TokenValidationException("Ошибка валидации токена: " + e.getMessage());
    }
  }
}
