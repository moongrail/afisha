package ru.practicum.main.category.service;

import ru.practicum.main.category.dto.CategoryDto;

import java.util.List;

public interface CategoryPublicService {
    List<CategoryDto> findAllCategories(Integer from, Integer size);

    CategoryDto findCategoryById(Long catId);
}
