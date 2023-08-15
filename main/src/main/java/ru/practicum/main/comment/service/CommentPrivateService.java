package ru.practicum.main.comment.service;

import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentRequestCreateDto;
import ru.practicum.main.comment.dto.CommentRequestUpdateDto;

public interface CommentPrivateService {
    CommentDto createComment(Long userId, Long eventId, CommentRequestCreateDto requestCreateDto);

    CommentDto patchComment(Long userId, Long eventId, Long commentId, CommentRequestUpdateDto requestUpdateDto);

    void deleteComment(Long userId, Long eventId, Long commentId);
}
