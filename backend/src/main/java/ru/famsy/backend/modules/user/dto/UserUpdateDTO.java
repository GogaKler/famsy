package ru.famsy.backend.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.famsy.backend.errors.ErrorMessages;

public class UserUpdateDTO {
    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    @Size(min = 3, max = 50, message = "Не должно быть меньше 3 и больше 50")
    private String username;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
