package ru.practicum.publicpart.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.enums.SortEvent;
import ru.practicum.exceptions.BadRequestException;
import ru.practicum.models.EventFullDto;
import ru.practicum.models.EventShortDto;
import ru.practicum.publicpart.events.interfaces.PublicEventsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

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
        return publicEventsService.findById(id, request.getRequestURI(), request.getRemoteAddr());
    }

    @GetMapping
    public Collection<EventShortDto> findEvents(HttpServletRequest request,
                                                @RequestParam("text") String text,
                                                @RequestParam(value = "categories", required = false) Long[] categories,
                                                @RequestParam(value = "paid", required = false) Boolean paid,
                                                @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                                @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                                @RequestParam("onlyAvailable") Boolean onlyAvailable,
                                                @RequestParam("sort") String sort,
                                                @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size) {
        String clientIp = request.getRemoteAddr();
        String endpointPath = request.getRequestURI();

        if (from == null || size == null) {
            return null;
        } else {
            if (from < 0 || size <= 0) {
                throw new BadRequestException("Значения пагинации меньше 0");
            }
            SortEvent sortEvent = SortEvent.from(sort);
            Sort sortPageable = Sort.by(sortEvent.name()).descending();
            Pageable pageable = PageRequest.of(from / size, size, sortPageable);


            return publicEventsService.findEvents(text, categories, paid, rangeStart, rangeEnd,
                    onlyAvailable, pageable, clientIp, endpointPath);
        }
    }
}
