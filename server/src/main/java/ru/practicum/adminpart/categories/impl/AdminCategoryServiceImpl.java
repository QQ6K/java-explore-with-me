package ru.practicum.adminpart.categories.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.adminpart.categories.interfaces.AdminCategoryService;
import ru.practicum.exceptions.BadRequestException;
import ru.practicum.exceptions.WrongObjectException;
import ru.practicum.mappers.CategoryMapper;
import ru.practicum.models.Category;
import ru.practicum.models.CategoryDto;
import ru.practicum.models.NewCategoryDto;
import ru.practicum.repositories.CategoryRepository;
import ru.practicum.repositories.EventRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategory(newCategoryDto));
        log.debug("Создание категории id={}", category.getId());
        return CategoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new WrongObjectException("Категория не найдена id =" + categoryId));
        if (eventRepository.findByCategoryId(categoryId).isEmpty()) {
            categoryRepository.delete(category);
        }
        log.debug("Удаление категории id={}", category.getId());
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new BadRequestException("Категория не найдена id =" + categoryDto.getId()));
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        log.debug("Изменение категории id={}", category.getId());
        return CategoryMapper.toDto(category);
    }
}
