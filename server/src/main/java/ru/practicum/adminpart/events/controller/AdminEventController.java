package ru.practicum.adminpart.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminpart.events.interfaces.AdminEventService;
import ru.practicum.models.AdminUpdateEventRequest;
import ru.practicum.models.EventFullDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Slf4j
public class AdminEventController {
    private final AdminEventService adminEventService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(@Positive @PathVariable("eventId") Long eventId,
                                    @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        return adminEventService.updateEventRequest(eventId, adminUpdateEventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@Positive @PathVariable Long eventId) {
        return adminEventService.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@Positive @PathVariable Long eventId) {
        return adminEventService.rejectEvent(eventId);
    }

    @GetMapping
    public List<EventFullDto> findAllEvents(@RequestParam(value = "users", required = false) Long[] users,
                                            @RequestParam(value = "states", required = false) String[] states,
                                            @RequestParam(value = "categories", required = false) Long[] categories,
                                            @RequestParam(value = "rangeStart", required = false) String rangeStartString,
                                            @RequestParam(value = "rangeEnd", required = false) String rangeEndString,
                                            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Pageable pageable;
        if (size == null || from == null) {
            pageable = Pageable.unpaged();
        } else {
            int page = from / size;
            pageable = PageRequest.of(page, size);
        }
        LocalDateTime rangeStart = LocalDateTime.parse(rangeStartString,formatter);
        LocalDateTime rangeEnd = LocalDateTime.parse(rangeEndString,formatter);
        return adminEventService.findAllEvents(users, states, categories,
                rangeStart, rangeEnd, pageable);
    }
}
