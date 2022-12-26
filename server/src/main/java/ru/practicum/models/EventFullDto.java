package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.State;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
        private Long id;
        @NotBlank(message = "Пустая аннотация")
        private String annotation;
        @NotBlank(message = "Пустая категория")
        private CategoryDto category;
        private Integer confirmedRequests;
        private String createdOn;
        private String description;
        @NotBlank(message = "Отсутствует дата события")
        private String eventDate;
        @NotBlank(message = "Отсутствует инициатор")
        private UserShortDto initiator;
        @NotBlank(message = "Пустая локация")
        private Location location;
        @NotBlank(message = "Событие должно быть бесплатным или платным")
        private Boolean paid;
        private int participantLimit;
        private String publishedOn;
        private Boolean requestModeration;
        private State state;
        @NotBlank(message = "Пустой заголовок")
        private String title;
        private int views;
}
