package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    @NotNull(message = "Пустое id события")
    private Long eventId;
    private Boolean paid;
    private Long participantLimit;
    private String title;
}
