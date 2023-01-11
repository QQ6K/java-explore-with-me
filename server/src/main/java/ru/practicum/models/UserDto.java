package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "Адрес почты не может быть пустой")
    @Email(message = "Проверьте формат заполнения email")
    private String email;
    @NotBlank(message = "Пустое имя")
    private String name;
}
