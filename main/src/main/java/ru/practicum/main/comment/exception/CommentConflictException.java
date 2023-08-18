package ru.practicum.main.comment.exception;

public class CommentConflictException extends RuntimeException {
    public CommentConflictException(String message) {
        super(message);
    }
}
