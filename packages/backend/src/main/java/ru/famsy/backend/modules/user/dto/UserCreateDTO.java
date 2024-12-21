package ru.famsy.backend.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import ru.famsy.backend.errors.ErrorMessages;

public class UserCreateDTO extends UserDTO {

    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
