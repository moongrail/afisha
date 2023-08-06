package ru.practicum.main.event.exception;

public class EventDateConflictException extends RuntimeException {
    public EventDateConflictException(String message) {
        super(message);
    }
}
