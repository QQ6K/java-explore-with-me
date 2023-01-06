package ru.practicum.publicpart.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exceptions.BadRequestException;
import ru.practicum.models.CategoryDto;
import ru.practicum.publicpart.categories.interfaces.PublicCategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
@Slf4j
public class PublicCategoriesController {
    private final PublicCategoryService publicCategoryService;

    @GetMapping("/{catId}")
    public CategoryDto findCategoriesById(@PathVariable Long catId, HttpServletRequest request) {
        log.info("Запрос GET /categories/{}", catId);
        return publicCategoryService.getCategoryDtoById(catId);
    }

    public List<CategoryDto> findAllCategories(
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "20") Integer size,
            HttpServletRequest request
    ) {
        Pageable pageable;
        if (size == null || from == null) {
            pageable = Pageable.unpaged();
        } else if (size <= 0 || from < 0) {
            throw new BadRequestException("Ошибка параметров пагинации");
        } else {
            int page = from / size;
            pageable = PageRequest.of(page, size);
        }
        log.info("Запрос GET /categories/?from={}&size={}", from, size);
        return publicCategoryService.findAllCategories(pageable);
    }
}
