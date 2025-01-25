package ru.famsy.backend.modules.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.famsy.backend.common.exception.dto.ErrorResponse;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
  private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void commence(
          HttpServletRequest request,
          HttpServletResponse response,
          AuthenticationException authException
  ) throws IOException {
    try {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");

      ErrorResponse errorResponse = new ErrorResponse("Unauthorized", authException.getMessage());
      objectMapper.writeValue(response.getWriter(), errorResponse);
    } catch (IOException e) {
      logger.error("Ошибка при отправке ответа об ошибке аутентификации", e);
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
  }
}
