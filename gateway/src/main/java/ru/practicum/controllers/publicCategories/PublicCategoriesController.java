package ru.practicum.controllers.publicCategories;

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
import ru.practicum.exceptions.BadRequestException;

import javax.servlet.http.HttpServletRequest;

@Controller
@Validated
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class PublicCategoriesController {
    private final CategoriesClientP categoriesClientP;

    @GetMapping("/{catId}")
    public ResponseEntity<Object> findCategoriesById(@PathVariable Long catId, HttpServletRequest request) {
        log.info("Запрос GET /categories/{}", catId);
        return categoriesClientP.get(catId);
    }

    @GetMapping
    public ResponseEntity<Object> findAllCategories(@RequestParam(defaultValue = "0") Integer from, @RequestParam(defaultValue = "20") Integer size, HttpServletRequest request) {
        Pageable pageable;
        if (size == null || from == null) {
            pageable = Pageable.unpaged();
        } else if (size <= 0 || from < 0) {
            throw new BadRequestException("Ошибка параметров пагинации");
        } else {
            int page = from / size;
            pageable = PageRequest.of(page, size);
        }
        Long id = null;
        log.info("Запрос GET /categories?from={}&size={}", from, size);
        String query = request.getQueryString();
        return categoriesClientP.get(query, id);
    }

}
