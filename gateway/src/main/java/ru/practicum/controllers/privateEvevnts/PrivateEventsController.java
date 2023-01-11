package ru.practicum.controllers.privateEvevnts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exceptions.BadRequestException;
import ru.practicum.models.NewEventDto;
import ru.practicum.models.UpdateEventRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Validated
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventsController {

    private final EventsClientPt eventsClientP;

    @GetMapping("/{userId}/events")
    public ResponseEntity<Object> findEventsByUserId(
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
        log.info("Запрос GET /users/{}/events с параметрами from = {}, size = {}", userId, from, size);
        String query = request.getQueryString();
        return eventsClientP.get(query, userId);
    }

    @PatchMapping("/{userId}/events")
    public ResponseEntity<Object> updateEvent(
            @Valid @RequestBody UpdateEventRequest updateEventRequest,
            @PathVariable Long userId,
            HttpServletRequest request
    ) {
        log.info("Запрос PATCH /users/{}/events", userId);
        return eventsClientP.patch(updateEventRequest, userId);
    }

    @PostMapping("/{userId}/events")
    public ResponseEntity<Object> createEvent(
            @Valid @RequestBody NewEventDto newEventDto,
            @PathVariable Long userId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /users/{}/events", userId);
        return eventsClientP.post(newEventDto, userId);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public ResponseEntity<Object> findEventById(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        log.info("Запрос POST /users/{}/events/{}", userId, eventId);
        return eventsClientP.get(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public ResponseEntity<Object> cancelEventById(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            HttpServletRequest request
    ) {
        log.info("Запрос PATCH /users/{}/events/{}", userId, eventId);
        return eventsClientP.patch(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<Object> findRequestByUserIdAndEventId(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            HttpServletRequest request) {
        log.info("Запрос GET /users/{}/events/{}/requests", userId, eventId);
        return eventsClientP.get(userId, eventId, "requests");
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ResponseEntity<Object> confirmParticipationRequest(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long reqId,
            HttpServletRequest request
    ) {
        log.info("Запрос PATCH /users/{}/events/{}/requests/{}/confirm", userId, eventId, reqId);
        return eventsClientP.patch(userId, eventId, reqId, "confirm");
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ResponseEntity<Object> rejectParticipationRequest(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long reqId,
            HttpServletRequest request
    ) {
        log.info("Запрос PATCH /users/{}/events/{}/requests/{}/reject", userId, eventId, reqId);
        return eventsClientP.patch(userId, eventId, reqId, "reject");
    }
}
