package ru.famsy.backend.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import ru.famsy.backend.errors.ErrorMessages;

@Schema(description = "DTO для авторизации пользователя")
public class LoginDTO {
    @Schema(
            description = "Логин (email или username)",
            example = "john@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    private String login;

    @Schema(
            description = "Пароль пользователя",
            example = "password123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
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
