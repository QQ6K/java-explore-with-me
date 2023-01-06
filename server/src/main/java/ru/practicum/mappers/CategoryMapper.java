package ru.practicum.mappers;

import ru.practicum.models.Category;
import ru.practicum.models.CategoryDto;
import ru.practicum.models.NewCategoryDto;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category toCategory(NewCategoryDto newCategoryDto) {
        return new Category(0L, newCategoryDto.getName());
    }

}
