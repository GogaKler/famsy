package ru.famsy.backendjava.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import ru.famsy.backendjava.errors.ErrorMessages;

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
