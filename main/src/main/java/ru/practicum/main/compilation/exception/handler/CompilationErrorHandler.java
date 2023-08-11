package ru.practicum.main.compilation.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.common.ApiError;
import ru.practicum.main.compilation.exception.CompilationNotFoundException;
import ru.practicum.main.compilation.exception.CompilationUniqueTitleException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "ru.practicum.main")
public class CompilationErrorHandler {

    @ExceptionHandler(CompilationUniqueTitleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleCompilationUniqueTitleException(CompilationUniqueTitleException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.CONFLICT)
                .reason(ex.getCause().toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.from(Instant.now()))
                .build();
    }

    @ExceptionHandler(CompilationNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleCompilationNotFoundException(CompilationNotFoundException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.NOT_FOUND)
                .reason(ex.getCause().toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.from(Instant.now()))
                .build();
    }
}
