package ru.practicum.main.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.exception.CategoryNotFoundException;
import ru.practicum.main.category.exception.CategoryUniqueNameException;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repositories.CategoryRepository;

import static ru.practicum.main.category.mapper.CategoryMapperUtil.fromDtoRequest;
import static ru.practicum.main.category.mapper.CategoryMapperUtil.toCategoryDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto addCategory(NewCategoryDto request) {
        if (categoryRepository.existsByName(request.getName())) {
            log.error("Category with this name already exists {}", request.getName());
            throw new CategoryUniqueNameException("Category with this name already exists");
        }

        Category save = categoryRepository.save(fromDtoRequest(request));
        log.info("Added category: {}", save);
        return toCategoryDto(save);
    }

    @Override
    public void deleteCategory(Long catId) {
        if (!categoryRepository.existsById(catId)) {
            log.error("Category with this id does not exist {}", catId);
            throw new CategoryNotFoundException("Category with this id does not exist");
        }
        log.info("Deleted category with id: {}", catId);
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto patchCategory(Long catId, NewCategoryDto request) {
        if (!categoryRepository.existsById(catId)) {
            log.error("Category with this id does not exist {}", catId);
            throw new CategoryNotFoundException("Category with this id does not exist");
        }else if (!categoryRepository.existsByName(request.getName()) &&
                !categoryRepository.existsById(catId)) {
            log.error("Category with this name does not exist {}", request.getName());
            throw new CategoryUniqueNameException("Category with this name does not exist");
        }
        Category save = categoryRepository.save(fromDtoRequest(request));
        log.info("Updated category with id: {}", catId);

        return toCategoryDto(save);
    }
}
