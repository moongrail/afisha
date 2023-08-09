package ru.practicum.main.category.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.category.exception.CategoryNotFoundException;
import ru.practicum.main.category.exception.CategoryUniqueNameException;
import ru.practicum.main.common.ApiError;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@RestControllerAdvice(basePackages = "ru.practicum.main")
public class CategoryErrorHandler {

    @ExceptionHandler(CategoryUniqueNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleCategoryUniqueEmailException(CategoryUniqueNameException ex) {

        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.CONFLICT)
                .reason(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage())
                .message(ex.getMessage())
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.NOT_FOUND)
                .reason(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage())
                .message(ex.getMessage())
                .timestamp(now())
                .build();
    }
}
