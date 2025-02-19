package ru.famsy.backend.modules.space_member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.famsy.backend.common.dto.BaseDTO;
import ru.famsy.backend.modules.space.dto.SpaceDTO;
import ru.famsy.backend.modules.space_member.constants.SpaceMemberRole;
import ru.famsy.backend.modules.space_member.constants.SpaceMemberStatus;
import ru.famsy.backend.modules.user.dto.UserDTO;

@Schema(description = "DTO для участника пространства")
public class SpaceMemberDTO extends BaseDTO {

    @Schema(description = "Пользователь, являющийся участником пространства")
    private UserDTO user;

    @Schema(description = "Пространство, в котором состоит участник")
    private SpaceDTO space;

    @Schema(description = "Роль участника в пространстве", example = "ADMIN")
    private SpaceMemberRole role;

    @Schema(description = "Статус участника в пространстве", example = "ACTIVE")
    private SpaceMemberStatus status;

    public UserDTO getUser() {
        return user;
    }
    public void setUser(UserDTO user) {
        this.user = user;
    }

    public SpaceDTO getSpace() {
        return space;
    }
    public void setSpace(SpaceDTO space) {
        this.space = space;
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
}
