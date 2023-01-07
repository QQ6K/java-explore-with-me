package ru.practicum.adminpart.compilations.interfaces;

import ru.practicum.models.Compilation;
import ru.practicum.models.CompilationDto;
import ru.practicum.models.NewCompilationDto;

public interface AdminCompilationService {
    Compilation findById(Long compId);

    CompilationDto createCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(Long compId);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void addEventToCompilation(Long compId, Long eventId);

    void deletePinned(Long compId);

    void addPinned(Long compId);
}
