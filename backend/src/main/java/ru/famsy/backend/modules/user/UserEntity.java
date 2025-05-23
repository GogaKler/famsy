package ru.famsy.backend.modules.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import ru.famsy.backend.common.entity.BaseEntity;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
  @Schema(
          description = "Уникальное имя пользователя (логин)",
          example = "john_doe",
          requiredMode = Schema.RequiredMode.REQUIRED
  )
  @Column(nullable = false, unique = true)
  private String username;

  @Schema(
          description = "Электронная почта пользователя",
          example = "john@example.com",
          requiredMode = Schema.RequiredMode.REQUIRED
  )
  @Column(nullable = false, unique = true)
  private String email;

  @Schema(
          description = "Захешированный пароль пользователя",
          requiredMode = Schema.RequiredMode.REQUIRED
  )
  @Column(nullable = false)
  private String password;

  @Schema(
          description = "URL аватара пользователя",
          example = "https://example.com/avatar.jpg"
  )
  @Column(name = "avatar_url")
  private String avatarUrl;

  @Schema(
          description = "Имя файла аватара пользователя",
          example = "avatar.jpg"
  )
  @Column(name = "avatar_file_name")
  private String avatarFileName;

  public UserEntity() {}

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

  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
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
