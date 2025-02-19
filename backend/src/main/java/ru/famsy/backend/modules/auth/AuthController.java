package ru.famsy.backend.modules.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.famsy.backend.modules.auth.dto.LoginDTO;
import ru.famsy.backend.modules.auth.dto.RegisterDTO;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user.mapper.UserMapper;

@Tag(name = "auth", description = "API аутентификации и авторизации")
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;
  private final UserMapper userMapper;

  AuthController(AuthService authService, UserMapper userMapper) {
    this.authService = authService;
    this.userMapper = userMapper;
  }

  @Operation(
          summary = "Регистрация нового пользователя",
          description = "Создает нового пользователя и возвращает информацию о нем. Устанавливает JWT токены в cookies."
  )
  @PostMapping("/register")
  // TODO: Убирать пробелы в login и email вначале и конце -> TRIM
  public UserDTO register(
          @RequestBody @Valid RegisterDTO registerDTO,
          HttpServletRequest request,
          HttpServletResponse response
  ) {
    UserEntity userEntity = this.authService.register(registerDTO, request, response);
    return userMapper.toDTO(userEntity);
  }

  @Operation(
          summary = "Авторизация пользователя",
          description = "Авторизует пользователя и возвращает информацию о нем. Устанавливает JWT токены в cookies."
  )
  @PostMapping("/login")
  public UserDTO login(
          @RequestBody @Valid LoginDTO loginDTO,
          HttpServletRequest request,
          HttpServletResponse response) {
    UserEntity userEntity = authService.login(loginDTO, request, response);
    return userMapper.toDTO(userEntity);
  }

  @Operation(
          summary = "Выход из системы",
          description = "Удаляет текущую сессию пользователя и очищает JWT токены"
  )
  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(
          HttpServletRequest request,
          HttpServletResponse response
  ) {
    authService.logout(request, response);
  }

  @Operation(
    summary = "Проверка авторизации пользователя",
    description = "Проверяет, авторизован ли пользователь, и возвращает его информацию"
  )
  @GetMapping("/check-auth")
  public ResponseEntity<?> checkAuth(
          @AuthenticationPrincipal UserEntity userEntity
  ) {
    // TODO: Разобраться, чтобы возвращать ошибку. Сейчас на фронте будет падать в цикл checkAuth
    if (userEntity == null) {
      return ResponseEntity.ok(null);
    }
    return ResponseEntity.ok(userMapper.toDTO(userEntity));
  }
}
