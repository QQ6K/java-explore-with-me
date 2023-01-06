package ru.practicum.adminpart.categories.interfaces;

import ru.practicum.models.CategoryDto;
import ru.practicum.models.NewCategoryDto;

public interface AdminCategoryService {

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Long categoryId);

    CategoryDto updateCategory(CategoryDto categoryDto);
}
