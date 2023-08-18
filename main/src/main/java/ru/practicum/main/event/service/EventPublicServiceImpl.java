package ru.practicum.main.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.EventTypeSort;
import ru.practicum.main.event.exception.EventDateParameterException;
import ru.practicum.main.event.exception.EventNotFoundException;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repositories.EventRepository;
import ru.practicum.main.event.repositories.EventSpecifications;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main.comment.mapper.CommentMapperUtil.toCommentDtoList;
import static ru.practicum.main.event.mapper.EventMapperUtil.toEventFullDto;
import static ru.practicum.main.event.mapper.EventMapperUtil.toEventShortDtoList;
import static ru.practicum.main.event.model.EventState.PUBLISHED;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EventPublicServiceImpl implements EventPublicService {
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<EventShortDto> findAllEvents(String text, Long[] categories, Boolean paid, LocalDateTime rangeStart,
                                             LocalDateTime rangeEnd, Boolean onlyAvailable, EventTypeSort sort,
                                             Integer from, Integer size) {

        if (rangeStart != null && rangeEnd != null) {
            if (rangeStart.isAfter(rangeEnd)) {
                throw new EventDateParameterException("rangeStart must be before rangeEnd");
            }
        }

        Specification<Event> specification = Specification.where(null);

        if (text != null) {
            specification = specification.and(EventSpecifications.textContainsIgnoreCase(text));
        }
        if (categories != null && categories.length > 0) {
            specification = specification.and(EventSpecifications.hasCategories(categories));
        }
        if (paid != null) {
            specification = specification.and(EventSpecifications.isPaid(paid));
        }
        if (rangeStart != null) {
            specification = specification.and(EventSpecifications.eventDateAfterOrEqual(rangeStart));
        }
        if (rangeEnd != null) {
            specification = specification.and(EventSpecifications.eventDateBeforeOrEqual(rangeEnd));
        }
        if (onlyAvailable != null && onlyAvailable) {
            specification = specification.and(EventSpecifications.hasAvailableRequests());
        }

        Pageable pageable;
        if (sort.equals(EventTypeSort.EVENT_DATE)) {
            pageable = PageRequest.of(from, size, Sort.Direction.ASC, "eventDate");
        } else {
            pageable = PageRequest.of(from, size, Sort.Direction.DESC, "views");
        }

        List<Event> content = eventRepository.findAll(specification, pageable).getContent();

        log.info("findAllEvents: {}", content);
        return toEventShortDtoList(content);
    }

    @Override
    public EventFullDto findEventById(Long id, Boolean existIp) {
        Event event = eventRepository.findByIdAndState(id, PUBLISHED).orElseThrow(() ->
                new EventNotFoundException("Event not found"));

        Event updateEvent = event;

        if (!existIp) {
            event.setViews(event.getViews() + 1);
            updateEvent = eventRepository.save(event);
        }

        List<Comment> commentsByEventId = commentRepository.findCommentsByEventId(id);
        EventFullDto eventFullDto = toEventFullDto(updateEvent);
        eventFullDto.setComments(toCommentDtoList(commentsByEventId));

        log.info("EventPublicServiceImpl findEventById: {}", updateEvent);
        return eventFullDto;
    }
}
