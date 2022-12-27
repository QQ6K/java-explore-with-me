package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.State;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
        private Long id;
        @NotBlank(message = "Пустая аннотация")
        @Size(min = 20, max = 2000, message = "Длина должна быть от {min} до {max} символов")
        private String annotation;
        @NotBlank(message = "Пустая категория")
        private CategoryDto category;
        private Integer confirmedRequests;
        private String createdOn;
        @Size(min = 20, max = 7000, message = "Длина должна быть от {min} до {max} символов")
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
        @Size(min = 3, max = 120, message = "Длина должна быть от {min} до {max} символов")
        private String title;
        private int views;
}
