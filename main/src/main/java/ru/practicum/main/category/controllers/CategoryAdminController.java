package ru.practicum.main.category.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.service.CategoryAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@Slf4j
@Validated
public class CategoryAdminController {
    private final CategoryAdminService categoryAdminService;

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Valid NewCategoryDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryAdminService.addCategory(request));
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<String> deleteCategory(@PathVariable @Positive Long catId) {
        categoryAdminService.deleteCategory(catId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Category deleted: " + catId);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable @Positive Long catId,
                                                      @RequestBody @Valid NewCategoryDto request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryAdminService.patchCategory(catId, request));
    }
}
