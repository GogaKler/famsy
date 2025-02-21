package ru.famsy.backend.modules.space.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.famsy.backend.common.dto.BaseDTO;
import ru.famsy.backend.modules.space.constants.SpaceStatus;
import ru.famsy.backend.modules.space.constants.SpaceType;
import ru.famsy.backend.modules.space_member.dto.SpaceMemberNestedDTO;
import ru.famsy.backend.modules.user.dto.UserDTO;
import java.util.Set;

@Schema(description = "DTO для представления данных пространства")
public class SpaceDTO extends BaseDTO {

    @Schema(description = "Название пространства", example = "Личное пространство")
    private String name;

    @Schema(description = "Тип пространства", example = "PERSONAL")
    private SpaceType type;

    @Schema(description = "Описание пространства", example = "Пространство для подсчета семейных расходов")
    private String description;

    @Schema(description = "Статус пространства", example = "ACTIVE")
    private SpaceStatus status;

    @Schema(description = "Идентификатор владельца пространства", example = "1")
    private Long ownerId;

    @Schema(description = "Данные владельца пространства")
    private UserDTO owner;

    @Schema(description = "Список участников пространства")
    private Set<SpaceMemberNestedDTO> members;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public SpaceType getType() {
        return type;
    }
    public void setType(SpaceType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public SpaceStatus getStatus() {
        return status;
    }
    public void setStatus(SpaceStatus status) {
        this.status = status;
    }

    public Long getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public UserDTO getOwner() {
        return owner;
    }
    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public Set<SpaceMemberNestedDTO> getMembers() {
        return members;
    }
    public void setMembers(Set<SpaceMemberNestedDTO> members) {
        this.members = members;
    }
}
