package ru.practicum.main.category.service;

import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;

public interface CategoryAdminService {
    CategoryDto addCategory(NewCategoryDto request);

    void deleteCategory(Long catId);

    CategoryDto patchCategory(Long catId, NewCategoryDto request);
}
