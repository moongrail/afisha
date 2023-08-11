package ru.practicum.main.user.exception;

public class PaginationParameterException extends RuntimeException {
    public PaginationParameterException(String message) {
        super(message);
    }
}
