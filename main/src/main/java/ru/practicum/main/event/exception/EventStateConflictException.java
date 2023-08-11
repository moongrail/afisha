package ru.practicum.main.event.exception;

public class EventStateConflictException extends RuntimeException {
    public EventStateConflictException(String message) {
        super(message);
    }
}
