package ru.practicum.controllers.privateRequests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@Validated
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class PrivateRequestController {

    private final RequestClient requestClient;

    @GetMapping("/{userId}/requests")
    public ResponseEntity<Object> findAllByParticipantId(@PathVariable Long userId, HttpServletRequest request) {
        log.info("Запрос GET /users/{}/requests", userId);
        return requestClient.get(userId);
    }

    @PostMapping("/{userId}/requests")
    public ResponseEntity<Object> createRequest(
            @PathVariable Long userId,
            @RequestParam Optional<Long> eventId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /users/{}/requests", userId);
        String query = request.getQueryString();
        return requestClient.post(userId, eventId, query);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancelRequestByUser(
            @PathVariable Long userId,
            @PathVariable Long requestId,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /{}/requests/{}/cancel", userId, requestId);
        return requestClient.patch(userId, requestId, "cancel");
    }

}
