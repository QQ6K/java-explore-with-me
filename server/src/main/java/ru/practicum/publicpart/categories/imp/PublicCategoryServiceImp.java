package ru.practicum.publicpart.categories.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exceptions.CrudException;
import ru.practicum.mappers.CategoryMapper;
import ru.practicum.models.Category;
import ru.practicum.models.CategoryDto;
import ru.practicum.publicpart.categories.interfaces.PublicCategoryService;
import ru.practicum.repositories.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class PublicCategoryServiceImp implements PublicCategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto getCategoryDtoById(Long catId) {
        log.debug("Поиск категории id={}", catId);
        CategoryDto categoryDto = CategoryMapper.toDto((categoryRepository.findById(catId)
                .orElseThrow(() -> new CrudException("Категория не найдена id=" + catId))));
        return categoryDto;
    }

    @Override
    public Category getCategoryById(Long catId) {
        log.debug("Поиск категории id={}", catId);
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new CrudException("Категория не найдена id=" + catId));
    }

    @Override
    public List<CategoryDto> findAllCategories(Pageable pageable) {
        //Page<Category> categories = categoryRepository.findAll(pageable);
        log.debug("Поиск категорий от {} размер {}", pageable.getPageNumber(), pageable.getPageSize());
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
