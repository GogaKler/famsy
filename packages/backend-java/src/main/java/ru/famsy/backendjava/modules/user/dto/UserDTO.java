package ru.famsy.backendjava.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.famsy.backendjava.errors.ErrorMessages;

public class UserDTO {
    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    @Size(min = 3, max = 50, message = "Не должно быть меньше 3 и больше 50")
    private String username;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = ErrorMessages.REQUIRED_FIELD)
    @Email(message = ErrorMessages.NOT_VALID)
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
