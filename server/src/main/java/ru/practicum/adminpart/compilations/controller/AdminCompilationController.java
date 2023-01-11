package ru.practicum.adminpart.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminpart.compilations.interfaces.AdminCompilationService;
import ru.practicum.models.CompilationDto;
import ru.practicum.models.NewCompilationDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@Validated
@Slf4j
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    @PostMapping
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Запрос POST /admin/compilations");
        return adminCompilationService.createCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@Positive @PathVariable("compId") Long compId) {
        log.info("Запрос POST /admin/compilations/{}",compId);
        adminCompilationService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@Positive @PathVariable("compId") Long compId,
                                           @Positive @PathVariable("eventId") Long eventId) {
        log.info("Запрос DELETE /admin/compilations/{}/events/{}",compId,eventId);
        adminCompilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@Positive @PathVariable("compId") Long compId,
                                      @Positive @PathVariable("eventId") Long eventId) {
        log.info("Запрос PATCH /admin/compilations/{}/events/{}",compId,eventId);
        adminCompilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void deletePinned(@Positive @PathVariable("compId") Long compId) {
        log.info("Запрос DELETE /admin/compilations/{}/pin",compId);
        adminCompilationService.deletePinned(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void addPinned(@Positive @PathVariable("compId") Long compId) {
        log.info("Запрос PATCH /admin/compilations/{}/pin",compId);
        adminCompilationService.addPinned(compId);
    }
}
