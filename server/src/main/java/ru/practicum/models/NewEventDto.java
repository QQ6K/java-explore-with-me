package ru.practicum.models;

import javax.validation.constraints.NotBlank;

public class NewEventDto {
    @NotBlank(message = "Пустая аннотация")
    private String annotation;
    @NotBlank(message = "Пустая категория")
    private Long category;
    @NotBlank(message = "Пустое описание")
    private String description;
    @NotBlank(message = "Пустая дата")
    private String eventDate;
    @NotBlank(message = "Не заполнено место проведения")
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    @NotBlank(message = "Пустой заголовок")
    private String title;
}
