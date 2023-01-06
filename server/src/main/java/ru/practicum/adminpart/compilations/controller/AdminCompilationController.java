package ru.practicum.adminpart.compilations.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminpart.compilations.interfaces.CompilationService;
import ru.practicum.models.CompilationDto;
import ru.practicum.models.NewCompilationDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@Validated
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return compilationService.createCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@Positive @PathVariable("compId") Long compId) {
        compilationService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@Positive @PathVariable("compId") Long compId,
                                           @Positive @PathVariable("eventId") Long eventId) {
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@Positive @PathVariable("compId") Long compId,
                                      @Positive @PathVariable("eventId") Long eventId) {
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void deletePinned(@Positive @PathVariable("compId") Long compId) {
        compilationService.deletePinned(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void addPinned(@Positive @PathVariable("compId") Long compId) {
        compilationService.addPinned(compId);
    }
}
