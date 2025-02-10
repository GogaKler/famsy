package ru.famsy.backend.modules.user.dto;

import ru.famsy.backend.common.dto.BaseDTO;

public class UserDTO extends BaseDTO {
  private String username;

  private String email;

  private String avatarUrl;

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
