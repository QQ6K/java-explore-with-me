package ru.practicum.admin.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto {
    private Long id;
    @NotEmpty(message = "Пустое поле имя")
    private String name;
}