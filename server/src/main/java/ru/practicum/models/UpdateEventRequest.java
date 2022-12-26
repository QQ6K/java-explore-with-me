package ru.practicum.models;

import javax.validation.constraints.NotNull;

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
