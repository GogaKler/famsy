package ru.famsy.backend.modules.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.famsy.backend.modules.auth.config.SecurityConstants;
import ru.famsy.backend.modules.auth.dto.AuthServiceResult;
import ru.famsy.backend.modules.auth.dto.LoginDTO;
import ru.famsy.backend.modules.auth.dto.RegisterDTO;
import ru.famsy.backend.modules.auth.dto.TokenPairDTO;
import ru.famsy.backend.modules.auth.exception.AuthAlreadyException;
import ru.famsy.backend.modules.device.DeviceInfo;
import ru.famsy.backend.modules.device.DeviceService;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user.mapper.UserMapper;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;
  private final DeviceService deviceService;
  private final UserMapper userMapper;

  AuthController(AuthService authService, DeviceService deviceService, UserMapper userMapper) {
    this.authService = authService;
    this.deviceService = deviceService;
    this.userMapper = userMapper;
  }

  // TODO: Убирать пробелы в login и email вначале и конце
  @PostMapping("/register")
  public ResponseEntity<UserDTO> register(
          @RequestBody @Valid RegisterDTO registerDTO,
          HttpServletRequest request,
          HttpServletResponse response
  ) {
    if (authService.isAlreadyUserAuth()) {
      throw new AuthAlreadyException();
    }

    DeviceInfo deviceInfo = deviceService.getOrCreateDeviceInfo(request, response);
    AuthServiceResult authServiceResult = this.authService.register(registerDTO, deviceInfo);
    addTokenCookies(response, authServiceResult.tokenPairDTO());
    return ResponseEntity.ok(userMapper.userEntityToUserDTO(authServiceResult.userEntity()));
  }

  @PostMapping("/login")
  public ResponseEntity<UserDTO> login(
          @RequestBody @Valid LoginDTO loginDTO,
          HttpServletRequest request,
          HttpServletResponse response) {
    if (authService.isAlreadyUserAuth()) {
      throw new AuthAlreadyException();
    }

    // TODO: Сетать в куки DeviceId только после всех валидаций
    DeviceInfo deviceInfo = deviceService.getOrCreateDeviceInfo(request, response);
    AuthServiceResult authServiceResult = authService.login(loginDTO, deviceInfo);
    addTokenCookies(response, authServiceResult.tokenPairDTO());
    UserDTO userDTO = userMapper.userEntityToUserDTO(authServiceResult.userEntity());
    return ResponseEntity.ok(userDTO);
  }

  @PostMapping("/logout")
  public ResponseEntity<SemanticContext.Empty> logout(
          @CookieValue("refresh_token") String refreshToken,
          HttpServletResponse response
  ) {
    authService.revokeRefreshToken(refreshToken);
    removeTokenCookies(response);
    // TODO: Удалять еще и DeviceID Cookie
    return ResponseEntity.ok().build();
  }

  private void addTokenCookies(HttpServletResponse response, TokenPairDTO tokenPairDTO) {
    ResponseCookie accessCookie = ResponseCookie.from("access_token", tokenPairDTO.getAccessToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(SecurityConstants.ACCESS_TOKEN_EXPIRATION / 1000)
            .build();

    ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", tokenPairDTO.getRefreshToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(SecurityConstants.REFRESH_TOKEN_EXPIRATION / 1000)
            .build();

    response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
    response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
  }

  private void removeTokenCookies(HttpServletResponse response) {
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
}
