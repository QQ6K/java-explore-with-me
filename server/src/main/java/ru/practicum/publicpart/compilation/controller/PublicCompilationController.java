package ru.practicum.publicpart.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.models.CompilationDto;
import ru.practicum.publicpart.compilation.interfaces.PublicCompilationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
@Slf4j
public class PublicCompilationController {
    private final PublicCompilationService publicCompilationService;

    @GetMapping
    public List<CompilationDto> findCompilationByParams(
            @RequestParam(defaultValue = "false") Boolean pinned,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "20") Integer size,
            HttpServletRequest request
    ) {
        log.info("Запрос GET /compilations с параметрами pinned = {}, from = {}, size = {}", pinned, from, size);
        Pageable pageable;
        if (size == null || from == null) {
            pageable = Pageable.unpaged();
        } else {
            int page = from / size;
            pageable = PageRequest.of(page, size);
        }
        return publicCompilationService.findCompilations(pinned, pageable);
    }

    @GetMapping("/{compId}")
    public CompilationDto findCompilationById(@PathVariable Long compId, HttpServletRequest request) {
        log.info("Запрос GET /compilations/{}", compId);
        return publicCompilationService.findCompilation(compId);
    }
}
