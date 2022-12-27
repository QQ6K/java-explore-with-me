package ru.practicum.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewEventDto {
    @NotBlank(message = "Пустая аннотация")
    @Size(min = 20, max = 2000, message = "Длина должна быть от {min} до {max} символов")
    private String annotation;
    @NotBlank(message = "Пустая категория")
    private Long category;
    @NotBlank(message = "Пустое описание")
    @Size(min = 20, max = 7000, message = "Длина должна быть от {min} до {max} символов")
    private String description;
    @NotBlank(message = "Пустая дата")
    private String eventDate;
    @NotBlank(message = "Не заполнено место проведения")
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    @NotBlank(message = "Пустой заголовок")
    @Size(min = 3, max = 120, message = "Длина должна быть от {min} до {max} символов")
    private String title;
}
