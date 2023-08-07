package ru.practicum.main.user.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.common.ApiError;
import ru.practicum.main.user.controller.UserAdminController;
import ru.practicum.main.user.exception.PaginationParameterException;
import ru.practicum.main.user.exception.UserNotFoundException;
import ru.practicum.main.user.exception.UserParameterException;
import ru.practicum.main.user.exception.UserUniqueParameterEmailException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackageClasses = UserAdminController.class)
public class UserErrorHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleUserNotFoundException(UserNotFoundException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.NOT_FOUND)
                .reason(ex.getCause().toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.from(Instant.now()))
                .build();
    }

    @ExceptionHandler(PaginationParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleItemNotHeaderUserId(PaginationParameterException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.BAD_REQUEST)
                .reason(ex.getCause().toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.from(Instant.now()))
                .build();
    }

    @ExceptionHandler(UserUniqueParameterEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleUserUniqueParameterEmailException(UserUniqueParameterEmailException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.CONFLICT)
                .reason(ex.getCause().toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.from(Instant.now()))
                .build();
    }

    @ExceptionHandler(UserParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleUserParameterException(UserParameterException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.BAD_REQUEST)
                .reason(ex.getCause().toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.from(Instant.now()))
                .build();
    }
}
