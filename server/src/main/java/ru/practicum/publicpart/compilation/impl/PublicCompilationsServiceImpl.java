package ru.practicum.publicpart.compilation.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mappers.CompilationMapper;
import ru.practicum.models.CompilationDto;
import ru.practicum.publicpart.compilation.interfaces.PublicCompilationService;
import ru.practicum.repositories.CompilationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicCompilationsServiceImpl implements PublicCompilationService {

    private final CompilationRepository compilationRepository;

    @Override
    public List<CompilationDto> findCompilations(Boolean pinned, Pageable pageable) {
        log.info("Поиск припиненных подборок = {}", pinned);
        return compilationRepository.findAllByPinned(pinned, pageable).map(CompilationMapper::toDto).toList();
    }

    @Override
    public CompilationDto findCompilation(Long compId) {
        log.info("Поиск подборки id ={}", compId);
        return CompilationMapper.toDto(compilationRepository.findById(compId).orElse(null));
    }
}
