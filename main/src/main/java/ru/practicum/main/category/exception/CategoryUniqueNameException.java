package ru.practicum.main.category.exception;

public class CategoryUniqueNameException extends RuntimeException {
    public CategoryUniqueNameException(String message) {
        super(message);
    }
}
