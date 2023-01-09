package ru.practicum.controllers.adminCategories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.models.CategoryDto;
import ru.practicum.models.NewCategoryDto;

import javax.validation.Valid;

@Controller
@Validated
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoriesController {

    private final CategoriesClientA categoriesClient;

    @PostMapping
    public ResponseEntity<Object> createCategory(
            @RequestBody @Valid NewCategoryDto newCategoryDto
    ) {
        log.info("Запрос POST /admin/categories");
        return categoriesClient.create(newCategoryDto);
    }

    @PatchMapping
    public ResponseEntity<Object> updateCategory(
            @RequestBody @Valid CategoryDto categoryDto
    ) {
        log.info("Запрос PATCH /admin/categories");
        return categoriesClient.patch(categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long categoryId) {
        log.info("Запрос DELETE /admin/categories/{}", categoryId);
        return categoriesClient.delete(categoryId);
    }
}
