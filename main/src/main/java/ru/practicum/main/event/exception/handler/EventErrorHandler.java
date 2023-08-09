package ru.practicum.main.event.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.common.ApiError;
import ru.practicum.main.event.exception.EventConflictException;
import ru.practicum.main.event.exception.EventDatePatameterException;
import ru.practicum.main.event.exception.EventNotFoundException;
import ru.practicum.main.event.exception.EventStateConflictException;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@RestControllerAdvice(basePackages = "ru.practicum.main")
public class EventErrorHandler {

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleEventNotFoundException(EventNotFoundException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.CONFLICT)
                .reason(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage())
                .message(ex.getMessage())
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(EventStateConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleEventStateConflictException(EventStateConflictException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.CONFLICT)
                .reason(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage())
                .message(ex.getMessage())
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(EventDatePatameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleEventDateConflictException(EventDatePatameterException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.BAD_REQUEST)
                .reason(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage())
                .message(ex.getMessage())
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(EventConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleEventOwnerConflictException(EventConflictException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.NOT_FOUND)
                .reason(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage())
                .message(ex.getMessage())
                .timestamp(now())
                .build();
    }
}
