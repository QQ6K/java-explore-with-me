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
        return new CompilationDto(compilation.getId(), compilation.getTitle(), compilation.getPinned(), compilation.getEvents().stream()
                .map(EventMapper::toShortDto).collect(Collectors.toList())
        );
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        return new Compilation(0L, newCompilationDto.getTitle(), Optional.ofNullable(newCompilationDto.getPinned()).orElse(false), events);

    }
}
