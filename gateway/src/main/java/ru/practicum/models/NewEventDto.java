package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class NewEventDto {
    @NotBlank(message = "Пустая аннотация")
    @Size(min = 20, max = 2000, message = "Длина должна быть от {min} до {max} символов")
    private String annotation;
    @NotNull(message = "Пустая категория")
    private Long category;
    @NotBlank(message = "Пустое описание")
    @Size(min = 20, max = 7000, message = "Длина должна быть от {min} до {max} символов")
    private String description;
    @NotBlank(message = "Пустая дата")
    private String eventDate;
    @NotNull(message = "Не заполнено место проведения")
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotBlank(message = "Пустой заголовок")
    @Size(min = 3, max = 120, message = "Длина должна быть от {min} до {max} символов")
    private String title;
}
