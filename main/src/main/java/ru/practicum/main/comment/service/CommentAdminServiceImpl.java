package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.main.comment.dto.CommentFullDto;
import ru.practicum.main.comment.exception.CommentNotFoundException;
import ru.practicum.main.comment.mapper.CommentMapperUtil;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main.comment.repository.CommentSpecification.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentAdminServiceImpl implements CommentAdminService {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentFullDto> findAll(Long[] users, Long[] events, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        Integer from, Integer size) {

        Specification<Comment> specification = Specification.where(null);

        if (users != null && users.length > 0) {
            specification = specification.and(hasUsers(users));
        }
        if (events != null && events.length > 0) {
            specification = specification.and(hasEvent(events));
        }
        if (rangeStart != null) {
            specification = specification.and(commentDateAfterOrEqual(rangeStart));
        }
        if (rangeEnd != null) {
            specification = specification.and(commentDateBeforeOrEqual(rangeEnd));
        }

        Pageable pageable = PageRequest.of(from, size);

        Page<Comment> page = commentRepository.findAll(specification, pageable);

        return CommentMapperUtil.toCommentFullDtoList(page.getContent());
    }

    @Override
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new CommentNotFoundException("Comment not found");
        }

        commentRepository.deleteById(commentId);
    }
}
