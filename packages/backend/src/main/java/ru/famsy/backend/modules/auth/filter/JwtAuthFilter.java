package ru.famsy.backend.modules.auth.filter;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.famsy.backend.modules.auth.provider.JwtTokenProvider;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user.UserService;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtTokenProvider jwtTokenProvider;
  private final UserService userService;

  JwtAuthFilter(JwtTokenProvider jwtTokenProvider, UserService userService) {
    super();
    this.jwtTokenProvider = jwtTokenProvider;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    String accessToken = extractTokenFromCookie(request, "access_token");

    if (accessToken != null && !accessToken.isEmpty()) {
      try {
        String username = jwtTokenProvider.getUsernameFromToken(accessToken);
        UserEntity user = userService.findUserByUsername(username);

          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                  user,
                  null,
                  Collections.emptyList()
          );
          SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (EntityNotFoundException e) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
        return;
      } catch (Exception e) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
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
