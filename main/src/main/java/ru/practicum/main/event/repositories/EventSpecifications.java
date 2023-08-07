package ru.practicum.main.event.repositories;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.EventState;

import java.time.LocalDateTime;

public class EventSpecifications {
    public static Specification<Event> hasUsers(Long[] users) {
        return (root, query, criteriaBuilder) -> root.get("initiator").get("id").in(users);
    }

    public static Specification<Event> hasStates(EventState[] states) {
        return (root, query, criteriaBuilder) -> root.get("state").in(states);
    }

    public static Specification<Event> hasCategories(Long[] categories) {
        return (root, query, criteriaBuilder) -> root.get("category").get("id").in(categories);
    }

    public static Specification<Event> eventDateAfterOrEqual(LocalDateTime rangeStart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), rangeStart);
    }

    public static Specification<Event> eventDateBeforeOrEqual(LocalDateTime rangeEnd) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), rangeEnd);
    }

    public static Specification<Event> textContainsIgnoreCase(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")), "%" + text.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + text.toLowerCase() + "%")
        );
    }

    public static Specification<Event> isPaid(Boolean paid) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("paid"), paid);
    }

    public static Specification<Event> hasAvailableRequests() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("confirmedRequests"), 0L);
    }
}
