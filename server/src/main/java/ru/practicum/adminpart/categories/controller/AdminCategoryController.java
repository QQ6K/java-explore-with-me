package ru.practicum.adminpart.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminpart.categories.impl.AdminCategoryServiceImpl;
import ru.practicum.models.CategoryDto;
import ru.practicum.models.NewCategoryDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryController {
    private final AdminCategoryServiceImpl adminCategoryServiceImpl;

    @PostMapping
    public CategoryDto createCategory(
            @RequestBody @Valid NewCategoryDto newCategoryDto,
            HttpServletRequest request
    ) {
        log.info("Запрос POST /admin/categories");
        return adminCategoryServiceImpl.createCategory(newCategoryDto);
    }

    @PatchMapping
    public CategoryDto updateCategory(
            @RequestBody @Valid CategoryDto categoryDto,
            HttpServletRequest request
    ) {
        log.info("Запрос PATCH /admin/categories");
        return adminCategoryServiceImpl.updateCategory(categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteUser(@PathVariable Long categoryId, HttpServletRequest request) {
        log.info("Запрос DELETE /admin/categories/{}", categoryId);
        adminCategoryServiceImpl.deleteCategory(categoryId);
    }
}
