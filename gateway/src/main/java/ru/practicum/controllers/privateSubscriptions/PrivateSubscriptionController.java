package ru.practicum.controllers.privateSubscriptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.enums.SortEvent;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static ru.practicum.enums.SortEvent.EVENT_DATE;
import static ru.practicum.enums.SortEvent.VIEWS;

@RestController
@RequestMapping(path = "/feed")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PrivateSubscriptionController {

    private final SubscriptionClient subscriptionClient;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findFeedEvents(
            @PathVariable @Valid @Positive(message = "id отрицательный или 0") Long userId,
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "rangeStart", required = false) String rangeStartString,
            @RequestParam(value = "rangeEnd", required = false) String rangeEndString,
            @RequestParam(value = "catId", required = false) Long[] catId,
            @RequestParam(value = "paid", required = false) Boolean paid,
            @RequestParam(value = "onlyAvailable", required = false) Boolean onlyAvailable,
            HttpServletRequest request
    ) {
        log.info("Запрос GET /feed/{} с параметрами " +
                        "rangeStart = {}, rangeEnd = {}, catId = {}, paid = {}, from = {}, size = {}," +
                        " sort = {}, onlyAvailable = {}",
                userId, rangeStartString, rangeEndString, catId, paid, from, size, sort, onlyAvailable);
        return subscriptionClient.get(userId, request.getQueryString());
    }

    @PostMapping("/{userId}/subscription/{subscriptionUserId}")
    public ResponseEntity<Object> addUserSubscription(
            @Valid @Positive(message = "id отрицательный или 0") @PathVariable Long userId,
            @Valid @Positive(message = "id отрицательный или 0") @PathVariable Long subscriptionUserId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /feed/{}/subscription/{} ", userId, subscriptionUserId);
        return subscriptionClient.post(userId, subscriptionUserId);
    }

    @DeleteMapping("/{userId}/subscription/{subscriptionId}")
    public void deleteUserSubscription(
            @Valid @Positive(message = "id отрицательный или 0") @PathVariable Long userId,
            @Valid @Positive(message = "id отрицательный или 0") @PathVariable Long subscriptionId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /feed/{}/subscription/{} ", userId, subscriptionId);
        subscriptionClient.delete(userId, subscriptionId);
    }

    @GetMapping("/{userId}/subscription")
    public ResponseEntity<Object> getUserSubscription(
            @Valid @Positive(message = "id отрицательный или 0") @PathVariable Long userId,
            HttpServletRequest request
    ) {
        log.info("Запрос GET /feed/{}/subscription ", userId);
        return subscriptionClient.get(userId);
    }

    @PostMapping("/{userId}/subscription/lock")
    public ResponseEntity<Object> lockUserSubscription(
            @Valid @Positive(message = "id отрицательный или 0") @PathVariable Long userId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /feed/{}subscription/lock", userId);
        return subscriptionClient.post(userId);
    }

    @DeleteMapping("/{userId}/subscription/unlock")
    public ResponseEntity<Object> unlockUserSubscription(
            @Valid @Positive(message = "id отрицательный или 0") @PathVariable Long userId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /feed/{}/subscription/unlock", userId);
        return subscriptionClient.delete(userId);
    }

    private Pageable doPageable(Integer from, Integer size, String sort) {
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
        return pageable;
    }

}
