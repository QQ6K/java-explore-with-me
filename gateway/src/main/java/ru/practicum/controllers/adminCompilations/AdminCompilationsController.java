package ru.practicum.controllers.adminCompilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.models.NewCompilationDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Controller
@Validated
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationsController {

    private final CompilationsClientA compilationsClient;

    @PostMapping
    public ResponseEntity<Object> createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Запрос POST /admin/compilations");
        return compilationsClient.create(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Object> deleteCompilation(@Positive @PathVariable("compId") Long compId) {
        log.info("Запрос POST /admin/compilations/{}",compId);
        return compilationsClient.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public ResponseEntity<Object> deleteEventFromCompilation(@Positive @PathVariable("compId") Long compId,
                                           @Positive @PathVariable("eventId") Long eventId) {
        log.info("Запрос DELETE /admin/compilations/{}/events/{}",compId,eventId);
        return compilationsClient.delete(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public ResponseEntity<Object> addEventToCompilation(@Positive @PathVariable("compId") Long compId,
                                      @Positive @PathVariable("eventId") Long eventId) {
        log.info("Запрос PATCH /admin/compilations/{}/events/{}",compId,eventId);
        return compilationsClient.patch(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public ResponseEntity<Object> deletePinned(@Positive @PathVariable("compId") Long compId) {
        log.info("Запрос DELETE /admin/compilations/{}/pin",compId);
        return compilationsClient.delete(compId, "pin");
    }

    @PatchMapping("/{compId}/pin")
    public ResponseEntity<Object> addPinned(@Positive @PathVariable("compId") Long compId) {
        log.info("Запрос PATCH /admin/compilations/{}/pin",compId);
        return compilationsClient.patch(compId, "pin");
    }
}
