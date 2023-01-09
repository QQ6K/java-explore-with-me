package ru.practicum.controllers.adminEvents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.models.AdminUpdateEventRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@Validated
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
public class AdminEventsController {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private final EventsClientA eventsClient;

    @PutMapping("/{eventId}")
    public ResponseEntity<Object> updateEvent(@Positive @PathVariable("eventId") Long eventId,
                                              @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        log.info("Запрос PUT /admin/events/{}", eventId);
        return eventsClient.put(eventId, adminUpdateEventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    public ResponseEntity<Object> publishEvent(@Positive @PathVariable Long eventId) {
        log.info("Запрос PATCH /admin/events/{}/publish", eventId);
        return eventsClient.patch(eventId, "publish");
    }

    @PatchMapping("/{eventId}/reject")
    public ResponseEntity<Object> rejectEvent(@Positive @PathVariable Long eventId) {
        log.info("Запрос PATCH /admin/events/{}/reject", eventId);
        return eventsClient.patch(eventId, "reject");
    }

    @GetMapping
    public ResponseEntity<Object> findAllEvents(HttpServletRequest request, @RequestParam(value = "users", required = false) Long[] users,
                                                @RequestParam(value = "states", required = false) String[] states,
                                                @RequestParam(value = "categories", required = false) Long[] categories,
                                                @RequestParam(value = "rangeStart", required = false) String rangeStartString,
                                                @RequestParam(value = "rangeEnd", required = false) String rangeEndString,
                                                @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("Запрос GET /admin/events с параметрами users = {}, states = {}, categories = {}," +
                        " rangeStart = {}, rangeEnd = {}, from = {}, size = {}",
                users, states, categories, rangeStartString, rangeEndString, from, size);
        Pageable pageable;
        Long id = null;
        if (size == null || from == null) {
            pageable = Pageable.unpaged();
        } else {
            int page = from / size;
            pageable = PageRequest.of(page, size);
        }
        LocalDateTime rangeStart;
        LocalDateTime rangeEnd;
        if (rangeEndString != null && rangeStartString != null) {
            rangeStart = LocalDateTime.parse(rangeStartString, formatter);
            rangeEnd = LocalDateTime.parse(rangeEndString, formatter);
        } else {
            rangeEnd = null;
            rangeStart = null;
        }
        String query = request.getQueryString();
        return eventsClient.get(query, id);
    }
}
