package ru.practicum.models;

import javax.validation.constraints.NotBlank;

public class NewCategoryDto {
    @NotBlank(message = "Пустое название категории")
    private String name;
}
