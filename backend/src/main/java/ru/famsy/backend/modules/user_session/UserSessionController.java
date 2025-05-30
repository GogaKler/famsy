package ru.famsy.backend.modules.user_session;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.famsy.backend.common.exception.base.UnauthorizedException;
import ru.famsy.backend.modules.jwt.JwtCookieService;
import ru.famsy.backend.modules.jwt.JwtTokenService;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user_session.dto.UserSessionDTO;
import ru.famsy.backend.modules.user_session.mapper.UserSessionMapper;

import java.util.List;

@Tag(name = "sessions", description = "API для управления сессиями пользователя")
@RestController
@RequestMapping("/sessions")
public class UserSessionController {
  private final UserSessionService userSessionService;
  private final UserSessionMapper userSessionMapper;
  private final JwtCookieService jwtCookieService;
  private final JwtTokenService jwtTokenService;

  public UserSessionController(UserSessionService userSessionService, UserSessionMapper userSessionMapper, JwtCookieService jwtCookieService, JwtTokenService jwtTokenService) {
    this.userSessionService = userSessionService;
    this.userSessionMapper = userSessionMapper;
    this.jwtCookieService = jwtCookieService;
    this.jwtTokenService = jwtTokenService;
  }

  @Operation(
      summary = "Получение сессий пользователя",
      description = "Возвращает список сессий, принадлежащих авторизованному пользователю."
  )
  @GetMapping
  public List<UserSessionDTO> getUserSessions(
          @AuthenticationPrincipal UserEntity user
  ) {
    List<UserSessionEntity> sessions = userSessionService.getUserSessions(user);
    return userSessionMapper.toDTOList(sessions);
  }

  @Operation(
      summary = "Завершение сессии по ID",
      description = "Завершает выбранную сессию пользователя по её идентификатору."
  )
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void terminateSession(
          @AuthenticationPrincipal UserEntity user,
          @PathVariable("id") Long id
  ) {
    userSessionService.deleteUserSession(id, user);
  }

  @Operation(
      summary = "Завершение всех сессий кроме текущей",
      description = "Завершает все сессии пользователя, кроме текущей, определяемой по refresh-токену, извлечённому из cookie."
  )
  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void terminateAllSessions(
          @AuthenticationPrincipal UserEntity user,
          HttpServletRequest request
  ) {
    String refreshToken = jwtCookieService.extractTokenFromCookie(request, "refresh_token");
    String sessionId = jwtTokenService.extractSessionIdFromToken(refreshToken);
    if (sessionId == null) {
      throw new UnauthorizedException("Такой сессии не существует");
    }
    userSessionService.terminateOtherSessions(user, sessionId);
  }
}