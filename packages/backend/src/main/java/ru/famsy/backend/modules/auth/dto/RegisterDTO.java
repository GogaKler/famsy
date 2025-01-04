package ru.famsy.backend.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.famsy.backend.errors.ErrorMessages;

@Schema(description = "DTO для регистрации нового пользователя")
public class RegisterDTO {
    @Schema(
            description = "Имя пользователя",
            example = "john_doe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    private String username;

    @Schema(
            description = "Email пользователя",
            example = "john@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    @Email(message = ErrorMessages.NOT_VALID)
    private String email;


    @Schema(
            description = "Пароль пользователя. Минимум 8 символов, должен содержать буквы и цифры",
            example = "password123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    @Size(max = 20, message = "Пароль не должен превышать 20 символа")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Пароль должен содержать минимум 8 символов, включая буквы и цифры")
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
