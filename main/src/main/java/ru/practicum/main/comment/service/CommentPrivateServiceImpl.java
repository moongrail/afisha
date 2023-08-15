package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentRequestCreateDto;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.event.repositories.EventRepository;
import ru.practicum.main.user.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentPrivateServiceImpl implements CommentPrivateService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public CommentDto createComment(Long userId, Long eventId, CommentRequestCreateDto requestCreateDto) {
        return null;
    }
}
