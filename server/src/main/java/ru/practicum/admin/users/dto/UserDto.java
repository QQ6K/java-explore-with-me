package ru.practicum.admin.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotEmpty(message = "Пустое поле email")
    @Email(message = "Проверьте email")
    private String email;
    @NotEmpty(message = "Пустое поле имя")
    private String name;
}
