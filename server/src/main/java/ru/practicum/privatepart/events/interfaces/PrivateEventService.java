package ru.practicum.privatepart.events.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.models.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PrivateEventService {

    Event findById(Long eventId);

    ParticipationRequest findParticipationRequestById(Long id);

    List<EventShortDto> findEventsByUserId(Long userId, Pageable pageable);

    EventFullDto updateEventByUser(UpdateEventRequest updateEventRequest, Long userId);

    @Transactional
    EventFullDto createEvent(NewEventDto newEventDto, Long userId);

    EventFullDto findEventByInitiatorById(Long eventId, Long userId);

    EventFullDto findEventById(HttpServletRequest request, Long eventId);

    @Transactional
    EventFullDto cancelEventById(Long eventId, Long userId);

    @Transactional
    ParticipationRequestDto confirmParticipationRequest(Long userId, Long eventId, Long reqId);

    @Transactional
    ParticipationRequestDto rejectParticipationRequest(Long userId, Long eventId, Long reqId);
}
