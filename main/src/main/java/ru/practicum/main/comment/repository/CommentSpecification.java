package ru.practicum.main.comment.repository;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.main.comment.model.Comment;

import java.time.LocalDateTime;

@UtilityClass
public class CommentSpecification {

    public static Specification<Comment> hasUsers(Long[] users) {
        return (root, query, criteriaBuilder) -> root.get("actor").get("id").in(users);
    }

    public static Specification<Comment> hasEvent(Long[] users) {
        return (root, query, criteriaBuilder) -> root.get("event").get("id").in(users);
    }

    public static Specification<Comment> commentDateAfterOrEqual(LocalDateTime rangeStart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), rangeStart);
    }

    public static Specification<Comment> commentDateBeforeOrEqual(LocalDateTime rangeEnd) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), rangeEnd);
    }
}
