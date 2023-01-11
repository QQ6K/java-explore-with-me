package ru.practicum.adminpart.events.interfaces;

import org.springframework.data.domain.Pageable;
import ru.practicum.models.AdminUpdateEventRequest;
import ru.practicum.models.Event;
import ru.practicum.models.EventFullDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {

    EventFullDto updateEventRequest(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

    List<EventFullDto> findAllEvents(Long[] users, String[] states, Long[] categories,
                                     LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    List<Event> findEventsByIds(List<Long> ids);

    Event findEventById(Long eventId);
}
