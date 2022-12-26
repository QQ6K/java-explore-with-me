package ru.practicum.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewUserRequest {
    @NotNull(message = "Адрес почты не может быть пустой")
    @Email(message = "Проверьте формат заполнения email")
    private String email;
    @NotBlank(message = "Пустое имя")
    private String name;
}
