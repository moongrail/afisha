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

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@RestControllerAdvice(basePackages = "ru.practicum.main")
public class UserErrorHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleUserNotFoundException(UserNotFoundException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.NOT_FOUND)
                .reason(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage())
                .message(ex.getMessage())
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(PaginationParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleItemNotHeaderUserId(PaginationParameterException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.BAD_REQUEST)
                .reason(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage())
                .message(ex.getMessage())
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(UserUniqueParameterEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleUserUniqueParameterEmailException(UserUniqueParameterEmailException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.CONFLICT)
                .reason(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage())
                .message(ex.getMessage())
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(UserParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleUserParameterException(UserParameterException ex) {
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.BAD_REQUEST)
                .reason(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage())
                .message(ex.getMessage())
                .timestamp(now())
                .build();
    }
}
