package ru.practicum.main.comment.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.comment.exception.CommentConflictException;
import ru.practicum.main.comment.exception.CommentNotFoundException;
import ru.practicum.main.common.ApiError;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@RestControllerAdvice(basePackages = "ru.practicum.main")
public class CommentHandlerException {

    @ExceptionHandler(value = CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handlerCommentNotFoundException(CommentNotFoundException ex){
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.NOT_FOUND)
                .reason(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage())
                .message(ex.getMessage())
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(value = CommentConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handlerCommentNotFoundException(CommentConflictException ex){
        return ApiError.builder()
                .errors(Arrays.stream(ex.getStackTrace()).collect(Collectors.toList()))
                .status(HttpStatus.CONFLICT)
                .reason(ex.getCause() != null ? ex.getCause().toString() : ex.getMessage())
                .message(ex.getMessage())
                .timestamp(now())
                .build();
    }
}
