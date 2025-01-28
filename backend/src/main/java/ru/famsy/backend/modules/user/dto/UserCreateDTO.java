package ru.famsy.backend.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.famsy.backend.errors.ErrorMessages;

public class UserCreateDTO {

    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    @Size(min = 3, max = 50, message = "Не должно быть меньше 3 и больше 50")
    private String username;

    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    @Email(message = ErrorMessages.NOT_VALID)
    private String email;

    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    private String password;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

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
}
