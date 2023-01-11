package ru.practicum.mappers;

import ru.practicum.enums.State;
import ru.practicum.models.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class EventMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventFullDto toFullDto(Event event) {
        String publishedOn = null;
        if (event.getPublishedOn() != null) {
            publishedOn = formatter.format(event.getPublishedOn());
        }
        return new EventFullDto(event.getId(),
                event.getAnnotation(),
                CategoryMapper.toDto(event.getCategory()),
                (long)event.getParticipants().size(),
                formatter.format(event.getCreatedOn()),
                event.getDescription(),
                formatter.format(event.getEventDate()),
                UserMapper.toShortDto(event.getInitiator()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                publishedOn,
                event.getRequestModeration(),
                State.from(event.getState()).orElseGet(null),
                event.getTitle(),
                event.getViews());
    }

    public static EventShortDto toShortDto(Event event) {
        return new EventShortDto(event.getId(),
                event.getAnnotation(),
                event.getCategory(),
                event.getEventDate().format(formatter),
                (long) event.getParticipants().size(),
                UserMapper.toShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                event.getViews());
    }

    public static Event toEvent(NewEventDto newEventDto, Category category, User user) {
        return new Event(0L,
                newEventDto.getAnnotation(),
                category,
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter),
                newEventDto.getDescription(),
                LocalDateTime.parse(newEventDto.getEventDate(), formatter),
                user,
                newEventDto.getLocation(),
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                null,
                newEventDto.getRequestModeration(),
                "PENDING",
                newEventDto.getTitle(),
                0L,
                Collections.emptyList()
        );
    }

}
