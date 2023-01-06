package ru.practicum.mappers;

import ru.practicum.models.Compilation;
import ru.practicum.models.CompilationDto;
import ru.practicum.models.Event;
import ru.practicum.models.NewCompilationDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static CompilationDto toDto(Compilation compilation) {
        return new CompilationDto(compilation.getEvents().stream()
                .map(EventMapper::toShortDto).collect(Collectors.toList()),
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle());
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        return new Compilation(events, 0L, Optional.ofNullable(newCompilationDto.getPinned()).orElse(false), newCompilationDto.getTitle());

    }
}
