package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto {
    @NotNull(message = "Пустое значение id")
    private Long id;
    @NotBlank(message = "Пустое имя")
    private String name;
}
