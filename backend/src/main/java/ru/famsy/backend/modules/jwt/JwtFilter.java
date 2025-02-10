package ru.famsy.backend.modules.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.famsy.backend.common.exception.base.UnauthorizedException;
import ru.famsy.backend.common.exception.dto.ErrorResponse;
import ru.famsy.backend.modules.auth.dto.TokenPairDTO;
import ru.famsy.backend.modules.jwt.exception.TokenExpiredException;
import ru.famsy.backend.modules.jwt.exception.TokenValidationException;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user.UserService;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {
  private final UserService userService;
  private final ObjectMapper objectMapper;
  private final JwtTokenService jwtTokenService;
  private final JwtCookieService jwtCookieService;

  JwtFilter(
          UserService userService,
          JwtTokenService jwtTokenService,
          ObjectMapper objectMapper,
          JwtCookieService jwtCookieService
  ) {
    super();
    this.jwtTokenService = jwtTokenService;
    this.jwtCookieService = jwtCookieService;
    this.userService = userService;
    this.objectMapper = objectMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    try {
      String accessToken = jwtCookieService.extractTokenFromCookie(request, "access_token");
      String refreshToken = jwtCookieService.extractTokenFromCookie(request, "refresh_token");

      if (accessToken == null || accessToken.isEmpty()) {
        if (refreshToken != null && !refreshToken.isEmpty()) {
          handleTokenRefresh(request, response);
        }
        filterChain.doFilter(request, response);
        return;
      }

      try {
        Claims tokenClaims = jwtTokenService.validateAccessToken(accessToken);
        UserEntity userEntity = userService.findUserById(Long.parseLong(tokenClaims.getSubject()));
        Authentication authentication = createSecurityAuthentication(userEntity);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (TokenExpiredException e) {
        handleTokenRefresh(request, response);
      }

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      clearSecurityAuthentication(response);
      handleError(response, new UnauthorizedException(e.getMessage()));
    }
  }

  private void handleTokenRefresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String refreshToken = jwtCookieService.extractTokenFromCookie(request, "refresh_token");
    if (refreshToken == null || refreshToken.isEmpty()) {
      throw new TokenValidationException("Refresh token отсутствует");
    }

    TokenPairDTO tokenPairDTO = jwtTokenService.refreshTokens(refreshToken, request);
    jwtCookieService.addTokenCookies(tokenPairDTO, response);

    Claims claims = jwtTokenService.getTokenPayload(tokenPairDTO.getRefreshToken());
    UserEntity userEntity = userService.findUserById(Long.parseLong(claims.getSubject()));
    Authentication authentication = createSecurityAuthentication(userEntity);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private Authentication createSecurityAuthentication(UserEntity user) {
    return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) {
      @Override
      public String getName() {
        return user.getId().toString();
      }
    };
  }

  private void clearSecurityAuthentication(HttpServletResponse response) {
    SecurityContextHolder.clearContext();
    jwtCookieService.clearTokenCookies(response);
  }

  private void handleError(HttpServletResponse response, Exception e) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json;charset=UTF-8");
    response.setCharacterEncoding("UTF-8");

    ErrorResponse errorResponse = new ErrorResponse("Unauthorized", e.getMessage());
    objectMapper.writeValue(response.getWriter(), errorResponse);
  }
}
