package ru.practicum.main.event.model;

public enum EventState {
    PENDING,
    PUBLISHED,
    CANCELED;

    private String value;

    public String getValue() {
        return value;
    }
}
