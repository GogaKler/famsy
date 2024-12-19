package ru.famsy.backendjava.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
    private Long id;

    @NotBlank(message = "Поле username - обязательно")
    @Size(min = 3, max = 50, message = "Не должно быть меньше 3 и больше 50")
    private String username;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "Поле Email - обязательно")
    @Email(message = "Email должен быть действительным")
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
