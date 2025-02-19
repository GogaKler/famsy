package ru.famsy.backend.modules.space.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.famsy.backend.errors.ErrorMessages;
import ru.famsy.backend.modules.space.constants.SpaceType;

@Schema(description = "DTO для создания нового пространства")
public class SpaceCreateDTO {
    @Schema(description = "Название пространства", example = "Личное пространство", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    private String name;
    
    @Schema(description = "Тип пространства", example = "PERSONAL", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = ErrorMessages.REQUIRED_FIELD)
    private SpaceType type;
    
    @Schema(description = "Описание пространства", example = "Пространство для разработки и тестирования")
    private String description;

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
}
