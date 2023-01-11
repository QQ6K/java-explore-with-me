package ru.practicum.publicpart.compilation.interfaces;

import org.springframework.data.domain.Pageable;
import ru.practicum.models.CompilationDto;

import java.util.List;

public interface PublicCompilationService {
    List<CompilationDto> findCompilations(Boolean pinned, Pageable pageable);

    CompilationDto findCompilation(Long compId);

}
