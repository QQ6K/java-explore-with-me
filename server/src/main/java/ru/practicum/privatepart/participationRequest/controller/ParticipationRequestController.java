package ru.practicum.privatepart.participationRequest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.models.ParticipationRequestDto;
import ru.practicum.privatepart.participationRequest.interfaces.ParticipationRequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
@Slf4j
public class ParticipationRequestController {
    private final ParticipationRequestService participationRequestService;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> findAllByParticipantId(@PathVariable Long userId, HttpServletRequest request) {
        log.info("Запрос GET /users/{}/requests", userId);
        return participationRequestService.findAllByParticipantId(userId);
    }

    @PostMapping("/requests")
    public ParticipationRequestDto createRequest(
            @PathVariable Long userId,
            @RequestParam Optional<Long> eventId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /users/{}/requests", userId);
        return participationRequestService.createParticipation(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelRequestByUser(
            @PathVariable Long userId,
            @PathVariable Long requestId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /{}/requests/{}/cancel", userId, requestId);
        return participationRequestService.cancelRequestByUser(userId, requestId);
    }

}