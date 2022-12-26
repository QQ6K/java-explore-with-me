package ru.practicum.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryDto {
    @NotNull(message = "id = null")
    private Long id;
    @NotBlank(message = "Поле name пустое")
    private String name;
}
