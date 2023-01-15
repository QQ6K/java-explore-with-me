package ru.practicum.privatepart.subscriptions.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.enums.SortEvent;
import ru.practicum.models.EventShortDto;
import ru.practicum.models.SearchParameters;
import ru.practicum.models.Subscription;
import ru.practicum.models.UserDto;
import ru.practicum.privatepart.subscriptions.interfaces.PrivateSubscriptionService;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import static ru.practicum.enums.SortEvent.EVENT_DATE;
import static ru.practicum.enums.SortEvent.VIEWS;

@RestController
@RequestMapping(path = "/feed")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PrivateSubscriptionController {

    private final PrivateSubscriptionService privateSubscriptionService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/{userId}")
    public List<EventShortDto> findFeedEvents(
            @PathVariable Long userId,
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "rangeStart", required = false) String rangeStartString,
            @RequestParam(value = "rangeEnd", required = false) String rangeEndString,
            @RequestParam(value = "categories", required = false) Long[] catId,
            @RequestParam(value = "paid", required = false) Boolean paid,
            @RequestParam(value = "onlyAvailable", required = false) Boolean onlyAvailable,
            HttpServletRequest request
    ) {
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
        LocalDateTime rangeStart = null;
        LocalDateTime rangeEnd = null;
        if (rangeEndString != null && rangeStartString != null) {
            rangeStart = LocalDateTime.parse(URLDecoder.decode(rangeStartString, StandardCharsets.UTF_8), formatter);
            rangeEnd = LocalDateTime.parse(URLDecoder.decode(rangeEndString, StandardCharsets.UTF_8), formatter);
        }
        log.info("Запрос GET /feed/{} с параметрами " +
                        "rangeStart = {}, rangeEnd = {}, catId = {}, paid = {}, from = {}, size = {}, sort = {}", userId,
                rangeStartString, rangeEndString, catId, paid, from, size, sort);
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.setUserId(userId);
        searchParameters.setCatId(catId);
        searchParameters.setRangeStart(rangeStart);
        searchParameters.setRangeEnd(rangeEnd);
        searchParameters.setPageable(pageable);
        searchParameters.setOnlyAvailable(onlyAvailable);
        searchParameters.setPaid(paid);
        return privateSubscriptionService.getFeed(searchParameters);
    }

    @PostMapping("/{userId}/subscription/{subscriptionId}")
    public Subscription addUserSubscription(
            @PathVariable Long userId,
            @PathVariable Long subscriptionId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /feed/{}/subscription/{} ", userId, subscriptionId);
        return privateSubscriptionService.addUsersSubscription(userId, subscriptionId);
    }

    @DeleteMapping("/{userId}/subscription/{subscriptionId}")
    public void deleteUserSubscription(
            @PathVariable Long userId,
            @PathVariable Long subscriptionId,
            HttpServletRequest request
    ) {
        log.info("Запрос DELETE /feed/{}/subscription/{} ", userId, subscriptionId);
        privateSubscriptionService.deleteUsersSubscription(userId, subscriptionId);
    }

    @GetMapping("/{userId}/subscription")
    public Collection<Subscription> getUserSubscription(
            @PathVariable Long userId,
            HttpServletRequest request
    ) {
        log.info("Запрос GET /feed/{}/subscription ", userId);
        return privateSubscriptionService.getUsersSubscriptions(userId);
    }

    @PostMapping("/{userId}/subscription/lock")
    public UserDto lockUserSubscription(
            @PathVariable Long userId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /feed/{}subscription/lock", userId);
        return privateSubscriptionService.lockUserSubscription(userId);
    }

    @DeleteMapping("/{userId}/subscription/unlock")
    public UserDto unlockUserSubscription(
            @PathVariable Long userId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /feed/{}/subscription/unlock", userId);
        return privateSubscriptionService.unlockUserSubscription(userId);
    }
}
