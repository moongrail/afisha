package ru.practicum.main.event.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.common.ApiError;
import ru.practicum.main.event.controller.EventAdminController;
import ru.practicum.main.event.exception.EventDateConflictException;
import ru.practicum.main.event.exception.EventNotFoundException;
import ru.practicum.main.event.exception.EventConflictException;
import ru.practicum.main.event.exception.EventStateConflictException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackageClasses = EventAdminController.class)
public class EventErrorHandler {

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleEventNotFoundException(EventNotFoundException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.NOT_FOUND)
                .reason(ex.getCause().toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.from(Instant.now()))
                .build();
    }

    @ExceptionHandler(EventStateConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleEventStateConflictException(EventStateConflictException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.CONFLICT)
                .reason(ex.getCause().toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.from(Instant.now()))
                .build();
    }

    @ExceptionHandler(EventDateConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleEventDateConflictException(EventDateConflictException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.CONFLICT)
                .reason(ex.getCause().toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.from(Instant.now()))
                .build();
    }

    @ExceptionHandler(EventConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleEventOwnerConflictException(EventConflictException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.CONFLICT)
                .reason(ex.getCause().toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.from(Instant.now()))
                .build();
    }
}
