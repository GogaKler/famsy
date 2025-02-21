package ru.famsy.backend.modules.space.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.famsy.backend.errors.ErrorMessages;

@Schema(description = "DTO для обновления данных пространства")
public class SpaceUpdateDTO {

    @Schema(description = "Новое название пространства", example = "Новое название", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    @Size(min = 2, max = 50, message = "Не должно быть меньше 2 и больше 50")
    private String name;

    @Schema(description = "Новое описание пространства", example = "Описание обновлённого пространства")
    @Size(min = 1, max = 255, message = "Не должно быть меньше 1 и больше 255")
    private String description;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
