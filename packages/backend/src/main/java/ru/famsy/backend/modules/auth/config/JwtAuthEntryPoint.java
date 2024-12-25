package ru.famsy.backend.modules.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
  private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void commence(
          HttpServletRequest request,
          HttpServletResponse response,
          AuthenticationException authException
  ) {
    try {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");

      Map<String, String> error = new HashMap<>();
      error.put("error", "Unauthorized");
      error.put("message", authException.getMessage());

      String json = objectMapper.writeValueAsString(error);

      response.getWriter().write(json);
    } catch (IOException e) {
      logger.error("Ошибка при отправке ответа об ошибке аутентификации", e);
      try {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
      } catch (IOException exception) {
        logger.error("Неизвестная ошибка при отправке ответа", exception);
      }
    }
  }
}
