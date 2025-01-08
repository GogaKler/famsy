package ru.famsy.backend.modules.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import ru.famsy.backend.common.config.SecurityConfigProperties;
import ru.famsy.backend.modules.auth.dto.TokenPairDTO;

@Service
public class JwtCookieService {
  private final SecurityConfigProperties securityConfigProperties;

  public JwtCookieService(SecurityConfigProperties securityConfigProperties) {
    this.securityConfigProperties = securityConfigProperties;
  }

  public void addTokenCookies(TokenPairDTO tokenPairDTO, HttpServletResponse response) {
    ResponseCookie accessCookie = ResponseCookie.from("access_token", tokenPairDTO.getAccessToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(securityConfigProperties.getAccessTokenExpiration().getSeconds())
            .build();

    ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", tokenPairDTO.getRefreshToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(securityConfigProperties.getRefreshTokenExpiration().getSeconds())
            .build();

    response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
    response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
  }

  public void clearTokenCookies(HttpServletResponse response) {
    ResponseCookie accessCookie = ResponseCookie.from("access_token", "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .build();

    ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .build();

    response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
    response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
  }

  public String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(cookieName)) {
          return cookie.getValue();
        }
      }
    }

    return null;
  }
}
