package ru.practicum.main.user.exception;

public class UserUniqueParameterEmailException extends RuntimeException {
    public UserUniqueParameterEmailException(String message) {
        super(message);
    }
}
