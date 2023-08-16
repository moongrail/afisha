package ru.practicum.main.comment.service;

import ru.practicum.main.comment.dto.CommentFullDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentAdminService {
    List<CommentFullDto> findAll(Long[] users, Long[] events, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                 Integer from, Integer size);

    void deleteComment(Long commentId);
}
