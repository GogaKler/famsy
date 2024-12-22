package ru.famsy.backend.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import ru.famsy.backend.errors.ErrorMessages;

public class LoginDTO {
    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    private String login;

    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    private String password;

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
