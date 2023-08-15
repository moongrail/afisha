package ru.practicum.main.comment.service;

import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentRequestCreateDto;

public interface CommentPrivateService {
    CommentDto createComment(Long userId, Long eventId, CommentRequestCreateDto requestCreateDto);
}
