package ru.famsy.backend.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.famsy.backend.common.dto.BaseDTO;

@Schema(description = "DTO для представления данных пользователя")
public class UserDTO extends BaseDTO {

  @Schema(description = "Имя пользователя", example = "john_doe")
  private String username;

  @Schema(description = "Email пользователя", example = "john@example.com")
  private String email;

  @Schema(description = "URL аватара пользователя", example = "https://example.com/avatar.jpg")
  private String avatarUrl;

  @Schema(description = "Имя файла аватара пользователя", example = "avatar.jpg")
  private String avatarFileName;

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }
  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getAvatarFileName() {
    return avatarFileName;
  }
  public void setAvatarFileName(String avatarFileName) {
    this.avatarFileName = avatarFileName;
  }
}
