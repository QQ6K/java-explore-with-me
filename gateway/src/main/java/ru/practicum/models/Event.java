package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.State;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Long id;
    private String annotation;
    private Category category;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private User initiator;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private Long views;
    private Collection<User> participants;
}
