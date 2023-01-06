package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
