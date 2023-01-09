package ru.practicum.controllers.publicEvents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.enums.SortEvent;

import javax.servlet.http.HttpServletRequest;

import static ru.practicum.enums.SortEvent.EVENT_DATE;
import static ru.practicum.enums.SortEvent.VIEWS;

@Controller
@Validated
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class PublicEventsController {
    private final EventsClientPc eventsClientPc;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(HttpServletRequest request,
                                           @PathVariable("id") Long id) {
        log.info("Запрос GET /events/{}", id);
        return eventsClientPc.get(id);
    }

    @GetMapping
    public ResponseEntity<Object> findEvents(HttpServletRequest request,
                                             @RequestParam(value = "text") String text,
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
        Pageable pageable;
        Long id = 1L;
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
        String query = request.getQueryString();
        return eventsClientPc.get(id, query);

    }
}
