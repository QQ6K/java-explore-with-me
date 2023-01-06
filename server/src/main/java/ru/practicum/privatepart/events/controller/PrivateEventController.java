package ru.practicum.privatepart.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exceptions.BadRequestException;
import ru.practicum.models.*;
import ru.practicum.privatepart.events.interfaces.PrivateEventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventController {
    private final PrivateEventService privateEventService;

    @GetMapping
    public List<EventShortDto> findEventsByUserId(
            @PathVariable Long userId,
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @RequestParam(name = "size", defaultValue = "20") Integer size,
            HttpServletRequest request
    ) {
        Pageable pageable;
        if (size == null || from == null) {
            pageable = Pageable.unpaged();
        } else if (size <= 0 || from < 0) {
            throw new BadRequestException("Ошибка параметров пагинации");
        } else {
            int page = from / size;
            pageable = PageRequest.of(page, size);
        }
        log.info("Запрос GET /users/{}/events", userId);
        return privateEventService.findEventsByUserId(userId, pageable);
    }

    @PatchMapping
    public EventFullDto updateEvent(
            @Valid @RequestBody UpdateEventRequest updateEventRequest,
            @PathVariable Long userId,
            HttpServletRequest request
    ) {
        log.info("Запрос PATCH /users/{}/events", userId);
        return privateEventService.updateEventByUser(updateEventRequest, userId);
    }

    @PostMapping
    public EventFullDto createEvent(
            @Valid @RequestBody NewEventDto eventDto,
            @PathVariable Long userId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /users/{}/events", userId);
        return privateEventService.createEvent(eventDto, userId);
    }

    @GetMapping("/{eventId}")
    public EventFullDto findEventById(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            HttpServletRequest request) {
        log.info("Запрос POST /users/{}/events/{}", userId, eventId);
        return privateEventService.findEventByInitiatorById(eventId, userId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEventById(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            HttpServletRequest request
    ) {
        log.info("Запрос PATCH /users/{}/events/{}", userId, eventId);
        return privateEventService.cancelEventById(eventId, userId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmParticipationRequest(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long reqId,
            HttpServletRequest request
    ) {
        log.info("Запрос PATCH /users/{}/events/{}/requests/{}/confirm", userId, eventId, reqId);
        return privateEventService.confirmParticipationRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto rejectParticipationRequest(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long reqId,
            HttpServletRequest request
    ) {
        log.info("Запрос PATCH /users/{}/events/{}/requests/{}/reject", userId, eventId, reqId);
        return privateEventService.rejectParticipationRequest(userId, eventId, reqId);
    }

}
