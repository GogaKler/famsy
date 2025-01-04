package ru.famsy.backend.modules.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.famsy.backend.modules.auth.dto.LoginDTO;
import ru.famsy.backend.modules.auth.dto.RegisterDTO;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user.mapper.UserMapper;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;
  private final UserMapper userMapper;

  AuthController(AuthService authService, UserMapper userMapper) {
    this.authService = authService;
    this.userMapper = userMapper;
  }

  // TODO: Убирать пробелы в login и email вначале и конце -> TRIM
  @PostMapping("/register")
  public ResponseEntity<UserDTO> register(
          @RequestBody @Valid RegisterDTO registerDTO,
          HttpServletRequest request,
          HttpServletResponse response
  ) {
    UserEntity userEntity = this.authService.register(registerDTO, request, response);
    return ResponseEntity.ok(userMapper.toDTO(userEntity));
  }

  @PostMapping("/login")
  public ResponseEntity<UserDTO> login(
          @RequestBody @Valid LoginDTO loginDTO,
          HttpServletRequest request,
          HttpServletResponse response) {
    UserEntity userEntity = authService.login(loginDTO, request, response);
    return ResponseEntity.ok(userMapper.toDTO(userEntity));
  }

  @PostMapping("/logout")
  public ResponseEntity<SemanticContext.Empty> logout(
          HttpServletRequest request,
          HttpServletResponse response
  ) {
    authService.logout(request, response);
    return ResponseEntity.ok().build();
  }
}
