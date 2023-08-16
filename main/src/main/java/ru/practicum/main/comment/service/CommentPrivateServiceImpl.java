package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentFullDto;
import ru.practicum.main.comment.dto.CommentRequestCreateDto;
import ru.practicum.main.comment.dto.CommentRequestUpdateDto;
import ru.practicum.main.comment.exception.CommentConflictException;
import ru.practicum.main.comment.exception.CommentNotFoundException;
import ru.practicum.main.comment.mapper.CommentMapperUtil;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.event.exception.EventNotFoundException;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repositories.EventRepository;
import ru.practicum.main.user.exception.UserNotFoundException;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.*;
import static ru.practicum.main.comment.mapper.CommentMapperUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentPrivateServiceImpl implements CommentPrivateService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public CommentDto createComment(Long userId, Long eventId, CommentRequestCreateDto requestCreateDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new UserNotFoundException("Event not found"));

        Comment comment = fromCreateRequestDro(requestCreateDto);

        comment.setActor(user);
        comment.setEvent(event);

        Comment save = commentRepository.save(comment);

        return toCommentDto(save);
    }

    @Override
    public CommentDto patchComment(Long userId, Long eventId, Long commentId, CommentRequestUpdateDto requestUpdateDto) {
        checkUserExist(userId);
        checkEventExist(eventId);

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("Comment not found"));

        checkOwnerComment(userId, comment);

        comment.setText(requestUpdateDto.getText());
        comment.setModified(now());

        return toCommentDto(commentRepository.save(comment));
    }


    @Override
    public void deleteComment(Long userId, Long eventId, Long commentId) {
        checkUserExist(userId);
        checkEventExist(eventId);

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("Comment not found"));

        checkOwnerComment(userId, comment);

        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentFullDto findCommentByUserIdAndEventId(Long userId, Long eventId, Long commentId) {
        checkUserExist(userId);
        checkEventExist(eventId);

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("Comment not found"));

        checkOwnerComment(userId, comment);


        return toCommentFullDto(comment);
    }

    @Override
    public List<CommentFullDto> findAllCommentsByUserIdAndEventId(Long userId, Long eventId ,Integer from, Integer size) {
        checkUserExist(userId);
        checkEventExist(eventId);

        Pageable pageable = PageRequest.of(from, size, Sort.Direction.DESC, "id");

        List<Comment> comments = commentRepository
                .findCommentsByActor_IdAndEvent_Id(eventId, userId, pageable)
                .getContent();

        return toCommentFullDtoList(comments);
    }

    @Override
    public List<CommentFullDto> searchCommentsByUserIdAndEventIdAndText(Long userId, Long eventId, String text,
                                                                        Integer from, Integer size) {
        checkUserExist(userId);
        checkEventExist(eventId);

        Pageable pageable = PageRequest.of(from, size, Sort.Direction.DESC, "id");

        Page<Comment> commentsByActorIdAndEventIdAndTextContainingIgnoreCase = commentRepository
                .findCommentsByActor_IdAndEvent_IdAndTextContainingIgnoreCase(eventId, userId, text, pageable);

        return toCommentFullDtoList(commentsByActorIdAndEventIdAndTextContainingIgnoreCase.getContent());
    }

    private void checkEventExist(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException("Event not found");
        }
    }

    private void checkUserExist(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }
    }

    private static void checkOwnerComment(Long userId, Comment comment) {
        if (!comment.getActor().getId().equals(userId)) {
            throw new CommentConflictException("Comment conflict exception");
        }
    }
}
