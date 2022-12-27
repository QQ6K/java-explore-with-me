package ru.practicum.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EventShortDto {
    private Long id;
    @NotBlank(message = "Пустая аннотация")
    private String annotation;
    @NotBlank(message = "Пустая категория")
    private Category category;
    @NotBlank(message = "Отсутствует дата события")
    private String eventDate;
    private int confirmedRequests;
    private UserShortDto initiator;
    @NotBlank(message = "Событие должно быть бесплатным или платным")
    private Boolean paid;
    @NotBlank(message = "Пустой заголовок")
    @Size(min = 3, max = 120, message = "Длина должна быть от {min} до {max} символов")
    private String title;
    private int views;
}