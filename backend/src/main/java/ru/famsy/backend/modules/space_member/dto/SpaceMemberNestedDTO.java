package ru.famsy.backend.modules.space_member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.famsy.backend.common.dto.BaseDTO;
import ru.famsy.backend.modules.space_member.constants.SpaceMemberRole;
import ru.famsy.backend.modules.space_member.constants.SpaceMemberStatus;
import ru.famsy.backend.modules.user.dto.UserDTO;

@Schema(description = "Вложенный DTO в SpaceDTO для участника пространства")
public class SpaceMemberNestedDTO extends BaseDTO {
  @Schema(description = "Идентификатор пользователя", example = "1")
  private Long userId;

  @Schema(description = "Данные пользователя")
  private UserDTO user;

  @Schema(description = "Роль участника в пространстве", example = "USER")
  private SpaceMemberRole role;

  @Schema(description = "Статус участника в пространстве", example = "ACTIVE")
  private SpaceMemberStatus status;

  public UserDTO getUser() {
    return user;
  }
  public void setUser(UserDTO user) {
    this.user = user;
  }

  public SpaceMemberRole getRole() {
    return role;
  }
  public void setRole(SpaceMemberRole role) {
    this.role = role;
  }

  public SpaceMemberStatus getStatus() {
    return status;
  }
  public void setStatus(SpaceMemberStatus status) {
    this.status = status;
  }

  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
