package ru.practicum.main.category.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Slf4j
@Validated
public class CategoryPublicController {
    private final CategoryPublicService categoryPublicService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAllCategories(@RequestParam(defaultValue = "0")
                                                               @PositiveOrZero Integer from,
                                                               @RequestParam(defaultValue = "10")
                                                               @Positive Integer size) {
        return ResponseEntity
                .ok(categoryPublicService.findAllCategories(from, size));
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable @Positive Long catId) {
        return ResponseEntity
                .ok(categoryPublicService.findCategoryById(catId));
    }
}
