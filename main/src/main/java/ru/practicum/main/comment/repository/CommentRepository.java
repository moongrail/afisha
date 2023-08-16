package ru.practicum.main.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.comment.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Page<Comment> findCommentsByActor_IdAndEvent_Id(Long eventId, Long userId, Pageable pageable);
    Page<Comment> findCommentsByActor_IdAndEvent_IdAndTextContainingIgnoreCase(Long eventId, Long userId,
                                                                               String text, Pageable pageable);

    Page<Comment> findAll(Specification<Comment> specification, Pageable pageable);

    Optional<Comment> findCommentByActor_IdAndEvent_IdAndId(Long eventId, Long userId, Long commentId);
}
