package ru.practicum.main.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.exception.CategoryNotFoundException;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repositories.CategoryRepository;
import ru.practicum.main.event.dto.UpdateEventAdminRequest;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.model.StateActionAdmin;
import ru.practicum.main.event.exception.EventDateConflictException;
import ru.practicum.main.event.exception.EventNotFoundException;
import ru.practicum.main.event.exception.EventStateConflictException;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.EventState;
import ru.practicum.main.event.repositories.EventRepository;
import ru.practicum.main.event.repositories.EventSpecifications;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main.event.mapper.EventMapperUtil.toEventFullDto;
import static ru.practicum.main.event.mapper.EventMapperUtil.toEventFullDtoList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EventAdminServiceImpl implements EventAdminService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public EventFullDto patchEvent(Long eventId, UpdateEventAdminRequest requestToPatch) {
        log.info("patchEvent: eventId = {}, requestToPatch = {}", eventId, requestToPatch);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found id = " + eventId));


        checkStateAction(requestToPatch, event);
        checkEventDate(requestToPatch.getEventDate());

        if (requestToPatch.getAnnotation() != null) {
            event.setAnnotation(requestToPatch.getAnnotation());
        }
        if (requestToPatch.getCategory() != null) {
            Category category = categoryRepository.findById(requestToPatch.getCategory())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found id = "
                            + requestToPatch.getCategory()));
            event.setCategory(category);
        }
        if (requestToPatch.getDescription() != null) {
            event.setDescription(requestToPatch.getDescription());
        }
        if (requestToPatch.getEventDate() != null) {
            event.setEventDate(requestToPatch.getEventDate());
        }
        if (requestToPatch.getLocation() != null) {
            event.setLocation(requestToPatch.getLocation());
        }
        if (requestToPatch.getPaid() != null) {
            event.setPaid(requestToPatch.getPaid());
        }
        if (requestToPatch.getParticipantLimit() != null) {
            event.setParticipantLimit(requestToPatch.getParticipantLimit());
        }
        if (requestToPatch.getRequestModeration() != null) {
            event.setRequestModeration(requestToPatch.getRequestModeration());
        }

        Event updatedEvent = eventRepository.save(event);

        return toEventFullDto(updatedEvent);
    }

    @Override
    public List<EventFullDto> findAllEvents(Long[] users, EventState[] states, Long[] categories,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                            Integer from, Integer size) {

        Specification<Event> specification = Specification.where(null);

        if (users != null && users.length > 0) {
            specification = specification.and(EventSpecifications.hasUsers(users));
        }
        if (states != null && states.length > 0) {
            specification = specification.and(EventSpecifications.hasStates(states));
        }
        if (categories != null && categories.length > 0) {
            specification = specification.and(EventSpecifications.hasCategories(categories));
        }
        if (rangeStart != null) {
            specification = specification.and(EventSpecifications.eventDateAfterOrEqual(rangeStart));
        }
        if (rangeEnd != null) {
            specification = specification.and(EventSpecifications.eventDateBeforeOrEqual(rangeEnd));
        }

        Pageable pageable = PageRequest.of(from, size);
        Page<Event> eventPage = eventRepository.findAll(specification, pageable);

        return toEventFullDtoList(eventPage);
    }

    private static void checkStateAction(UpdateEventAdminRequest requestToPatch, Event event) {
        if (requestToPatch.getStateAction() == StateActionAdmin.PUBLISH_EVENT
                && event.getState() != EventState.PENDING) {
            log.error("Event is not pending");
            throw new EventStateConflictException("Event is not pending");
        }

        if (requestToPatch.getStateAction() == StateActionAdmin.REJECT_EVENT
                && event.getState() == EventState.PUBLISHED) {
            log.error("Event published");
            throw new EventStateConflictException("Event published");
        }
    }

    private void checkEventDate(LocalDateTime eventDate) {
        LocalDateTime checkDate = LocalDateTime.now().plusHours(2);
        if (eventDate.isBefore(checkDate)) {
            throw new EventDateConflictException("Event date conflict");
        }
    }
}
