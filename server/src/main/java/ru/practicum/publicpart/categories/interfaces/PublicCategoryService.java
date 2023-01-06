package ru.practicum.publicpart.categories.interfaces;

import org.springframework.data.domain.Pageable;
import ru.practicum.models.Category;
import ru.practicum.models.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    CategoryDto getCategoryDtoById(Long catId);

    Category getCategoryById(Long catId);

    List<CategoryDto> findAllCategories(Pageable pageable);
}
