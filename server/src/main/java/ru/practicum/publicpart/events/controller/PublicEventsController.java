package ru.practicum.publicpart.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.enums.SortEvent;
import ru.practicum.models.EventFullDto;
import ru.practicum.models.EventShortDto;
import ru.practicum.publicpart.events.interfaces.PublicEventsService;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static ru.practicum.enums.SortEvent.EVENT_DATE;
import static ru.practicum.enums.SortEvent.VIEWS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Slf4j
@Validated
public class PublicEventsController {
    private final PublicEventsService publicEventsService;

    @GetMapping("/{id}")
    public EventFullDto findById(HttpServletRequest request,
                                 @PathVariable("id") Long id) {
        log.info("Запрос GET /events/{}", id);
        return publicEventsService.findById(id, request.getRequestURI(), request.getRemoteAddr());
    }

    @GetMapping
    public Collection<EventShortDto> findEvents(HttpServletRequest request,
                                                @RequestParam(value = "text", required = false) String text,
                                                @RequestParam(value = "categories", required = false) Long[] categories,
                                                @RequestParam(value = "paid", required = false) Boolean paid,
                                                @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                                @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                                @RequestParam(value = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                                @RequestParam(value = "sort", required = false) String sort,
                                                @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("Запрос GET /events с параметрами  text = {}, categories = {}, paid = {}, rangeStart = {}," +
                        "rangeEnd = {}, onlyAvailable = {}, sort = {}, from = {}, size = {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        String clientIp = request.getRemoteAddr();
        String endpointPath = request.getRequestURI();
        Pageable pageable;
        if (size == null || from == null) {
            pageable = Pageable.unpaged();
        } else {
            int page = from / size;
            Sort sortPageable;
            if (sort != null) {
                SortEvent sortEvent = SortEvent.from(sort);
                if (sortEvent == EVENT_DATE) {
                    sort = "eventDate";
                } else if (sortEvent == VIEWS) {
                    sort = "view";
                }
                sortPageable = Sort.by(sort).descending();
                pageable = PageRequest.of(page, size, sortPageable);
            } else pageable = PageRequest.of(page, size);
        }
        if (text != null) {
            text = URLDecoder.decode(text, StandardCharsets.UTF_8);
        }
        return publicEventsService.findEvents(
                text,
                categories, paid, rangeStart, rangeEnd,
                onlyAvailable, pageable, clientIp, endpointPath);

    }
}
