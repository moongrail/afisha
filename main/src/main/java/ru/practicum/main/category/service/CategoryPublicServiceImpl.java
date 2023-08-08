package ru.practicum.main.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.exception.CategoryNotFoundException;
import ru.practicum.main.category.mapper.CategoryMapperUtil;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repositories.CategoryRepository;
import ru.practicum.main.common.util.PaginationUtil;

import java.util.List;

import static ru.practicum.main.category.mapper.CategoryMapperUtil.toCategoryDto;
import static ru.practicum.main.category.mapper.CategoryMapperUtil.toCategoryDtoList;
import static ru.practicum.main.common.util.PaginationUtil.getPaginationAsc;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryPublicServiceImpl implements CategoryPublicService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAllCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size, Sort.Direction.ASC, "id");

        List<Category> categories = categoryRepository.findAll(pageable).getContent();

        log.info("findAllCategories: {}", categories);
        return toCategoryDtoList(categories);
    }

    @Override
    public CategoryDto findCategoryById(Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() ->
                new CategoryNotFoundException("Category not found"));

        log.info("findCategoryById: {}", category);
        return toCategoryDto(category);
    }
}
