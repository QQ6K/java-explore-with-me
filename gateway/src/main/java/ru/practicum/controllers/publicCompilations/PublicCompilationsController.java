package ru.practicum.controllers.publicCompilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@Validated
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
public class PublicCompilationsController {
    private final CompilationsClientP compilationsClientP;

    @GetMapping
    public ResponseEntity<Object> findCompilationByParams(
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
        String query = request.getQueryString();
        return compilationsClientP.get(query, null);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<Object> findCompilationById(@PathVariable Long compId, HttpServletRequest request) {
        log.info("Запрос GET /compilations/{}", compId);
        return compilationsClientP.get(compId);
    }
}
