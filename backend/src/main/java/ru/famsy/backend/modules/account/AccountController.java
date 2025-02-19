package ru.famsy.backend.modules.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.famsy.backend.modules.user.UserEntity;
import ru.famsy.backend.modules.user.UserService;
import ru.famsy.backend.modules.user.dto.UserDTO;
import ru.famsy.backend.modules.user.mapper.UserMapper;

@Tag(name = "account", description = "API для управления аккаунтом пользователя")
@RestController
@RequestMapping("/account")
public class AccountController {
  private final UserService userService;
  private final UserMapper userMapper;

  public AccountController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @Operation(
      summary = "Загрузка аватара",
      description = "Позволяет загрузить новый аватар для авторизованного пользователя."
  )
  @PostMapping("/avatar")
  public ResponseEntity<UserDTO> uploadAvatar(
          @AuthenticationPrincipal UserEntity userEntity,
          MultipartFile file
  ) {
    UserEntity updatedUserEntity = userService.uploadAvatar(userEntity.getId(), file);
    return ResponseEntity.ok(userMapper.toDTO(updatedUserEntity));
  }

  @Operation(
      summary = "Удаление аватара",
      description = "Удаляет аватар для авторизованного пользователя."
  )
  @DeleteMapping("/avatar")
  public ResponseEntity<UserDTO> deleteAvatar(@AuthenticationPrincipal UserEntity userEntity) {
    UserEntity updatedUserEntity = userService.deleteAvatar(userEntity.getId());
    return ResponseEntity.ok(userMapper.toDTO(updatedUserEntity));
  }
}
