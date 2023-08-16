package ru.practicum.main.comment.service;

import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentFullDto;
import ru.practicum.main.comment.dto.CommentRequestCreateDto;
import ru.practicum.main.comment.dto.CommentRequestUpdateDto;

import java.util.List;

public interface CommentPrivateService {
    CommentDto createComment(Long userId, Long eventId, CommentRequestCreateDto requestCreateDto);

    CommentDto patchComment(Long userId, Long eventId, Long commentId, CommentRequestUpdateDto requestUpdateDto);

    void deleteComment(Long userId, Long eventId, Long commentId);

    CommentFullDto findCommentByUserIdAndEventId(Long userId, Long eventId, Long commentId);

    List<CommentFullDto> findAllCommentsByUserIdAndEventId(Long userId, Long eventId, Integer from, Integer size);

    List<CommentFullDto> searchCommentsByUserIdAndEventIdAndText(Long userId, Long eventId, String text,
                                                                 Integer from, Integer size);
}
