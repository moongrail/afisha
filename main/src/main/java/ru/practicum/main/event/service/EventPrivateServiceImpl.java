package ru.practicum.main.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.exception.CategoryNotFoundException;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repositories.CategoryRepository;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.exception.EventConflictException;
import ru.practicum.main.event.exception.EventDatePatameterException;
import ru.practicum.main.event.exception.EventNotFoundException;
import ru.practicum.main.event.exception.EventStateConflictException;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.EventState;
import ru.practicum.main.event.model.StateActionPrivate;
import ru.practicum.main.event.repositories.EventRepository;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.exception.RequestNotFoundException;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.model.RequestStatus;
import ru.practicum.main.request.repositories.RequestRepository;
import ru.practicum.main.user.exception.UserNotFoundException;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static ru.practicum.main.comment.mapper.CommentMapperUtil.toCommentDtoList;
import static ru.practicum.main.event.mapper.EventMapperUtil.*;
import static ru.practicum.main.event.model.EventState.PUBLISHED;
import static ru.practicum.main.request.mapper.RequestMapperUtil.toParticipationRequestDtoList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EventPrivateServiceImpl implements EventPrivateService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<EventShortDto> findAllByUserId(Long userId, Integer from, Integer size) {
        checkUserExist(userId);

        Pageable pageable = PageRequest.of(from, size, Sort.Direction.ASC, "id");

        List<Event> eventsByInitiatorId = eventRepository.findEventsByInitiatorId(userId, pageable).getContent();

        log.info("findAllByUserId-findAllByUserId: {}", eventsByInitiatorId);
        return toEventShortDtoList(eventsByInitiatorId);
    }

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        checkEventDate(newEventDto.getEventDate());
        checkEventTitle(newEventDto);

        Event newEvent = buildEventToCreated(userId, newEventDto);
        Event save = eventRepository.save(newEvent);

        log.info("createEvent: {}", save);
        return toEventFullDto(save);
    }

    @Override
    public EventFullDto findEventByUserIdAndEventId(Long userId, Long eventId) {
        checkUserExist(userId);
        Event event = eventRepository.findEventByInitiatorIdAndId(userId, eventId).orElseThrow(() ->
                new EventNotFoundException("Event not found by id = " + eventId));

        EventFullDto eventFullDto = toEventFullDto(event);
        List<Comment> commentsByEventId = commentRepository.findCommentsByEventId(eventId);
        eventFullDto.setComments(toCommentDtoList(commentsByEventId));

        log.info("findEventByUserIdAndEventId-findEventByUserIdAndEventId: {}", event);
        return eventFullDto;
    }

    @Override
    public EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        checkUserExist(userId);

        if (updateEventUserRequest.getEventDate() != null) {
            checkEventDate(updateEventUserRequest.getEventDate());
        }

        if (updateEventUserRequest.getTitle() != null) {
            if (eventRepository.existsEventByTitle(updateEventUserRequest.getTitle())) {
                log.warn("Event with title = " + updateEventUserRequest.getTitle() + " already exists");
                throw new EventConflictException("Event with title = " + updateEventUserRequest.getTitle() +
                        " already exists");
            }
        }

        Event oldEvent = eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException("Event not found by id = " + eventId));

        if (!userId.equals(oldEvent.getInitiator().getId())) {
            log.warn("EventConflictException: You can't edit this event");
            throw new EventConflictException("You can't edit this event");
        }

        if (oldEvent.getState().equals(PUBLISHED)) {
            log.warn("EventConflictException: {} You can't edit this event", PUBLISHED);
            throw new EventConflictException("You can't edit this event, must be PENDING or CANCELED");
        }

        Event updatedEvent = buildUpdateEvent(updateEventUserRequest, oldEvent);

        log.info("updateEvent: {}", updatedEvent);
        return toEventFullDto(eventRepository.save(updatedEvent));
    }

    @Override
    public List<ParticipationRequestDto> findAllByUserIdAndEventIdRequests(Long userId, Long eventId) {
        checkUserExist(userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException("Event not found by id = " + eventId));

        List<Request> requestsByEventId = new ArrayList<>();

        if (event.getInitiator().getId().equals(userId)) {
            requestsByEventId.addAll(requestRepository.findRequestsByEvent_Id(eventId));
        }

        requestsByEventId.addAll(requestRepository
                .findRequestsByRequester_IdAndEvent_Id(userId, eventId));

        log.info("findAllByUserIdAndEventIdRequests: {} userId: {} eventId: {}",
                requestsByEventId, userId, eventId);

        return toParticipationRequestDtoList(requestsByEventId);
    }

    @Override
    public EventRequestStatusUpdateResult updateEventRequests(Long userId, Long eventId,
                                                              EventRequestStatusUpdateRequest eventStatusUpdateRequest) {
        checkUserExist(userId);
        Event event = eventRepository
                .findEventByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found by id = " + eventId));

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            throw new UnsupportedOperationException("Confirmation of requests is not required for this event.");
        }

        List<Request> requestsToUpdate = requestRepository.findRequestsByEvent_IdAndIdIn(
                eventId, eventStatusUpdateRequest.getRequestIds());

        if (requestsToUpdate.isEmpty()) {
            log.warn("No requests found with the given IDs.");
            throw new RequestNotFoundException("No requests found with the given IDs.");
        }

        if (requestsToUpdate.stream().anyMatch(request -> request.getStatus() != RequestStatus.PENDING)) {
            log.warn("Request must have status PENDING.");
            throw new EventStateConflictException("Request must have status PENDING.");
        }

        if (event.getConfirmedRequests() + requestsToUpdate.size() > event.getParticipantLimit()) {
            log.warn("The participant limit has been reached.");
            throw new EventConflictException("The participant limit has been reached.");
        }

        RequestStatus newStatus = eventStatusUpdateRequest.getStatus();
        List<Request> confirmedRequests = new ArrayList<>();
        List<Request> rejectedRequests = new ArrayList<>();

        for (Request request : requestsToUpdate) {
            request.setStatus(newStatus);
            if (newStatus == RequestStatus.CONFIRMED) {
                confirmedRequests.add(request);
            } else if (newStatus == RequestStatus.REJECTED) {
                rejectedRequests.add(request);
            }
        }

        requestRepository.saveAll(requestsToUpdate);

        if (newStatus == RequestStatus.CONFIRMED) {
            event.setConfirmedRequests(event.getConfirmedRequests() + requestsToUpdate.size());
        }

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(toParticipationRequestDtoList(confirmedRequests))
                .rejectedRequests(toParticipationRequestDtoList(rejectedRequests))
                .build();
    }

    private Event buildUpdateEvent(UpdateEventUserRequest updateEventUserRequest, Event oldEvent) {
        if (updateEventUserRequest.getAnnotation() != null) {
            oldEvent.setAnnotation(updateEventUserRequest.getAnnotation());
        }
        if (updateEventUserRequest.getEventDate() != null) {
            oldEvent.setEventDate(updateEventUserRequest.getEventDate());
        }
        if (updateEventUserRequest.getDescription() != null) {
            oldEvent.setDescription(updateEventUserRequest.getDescription());
        }
        if (updateEventUserRequest.getLocation() != null) {
            oldEvent.setLocation(updateEventUserRequest.getLocation());
        }
        if (updateEventUserRequest.getTitle() != null) {
            oldEvent.setTitle(updateEventUserRequest.getTitle());
        }
        if (updateEventUserRequest.getParticipantLimit() != null) {
            oldEvent.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }
        if (updateEventUserRequest.getRequestModeration() != null) {
            oldEvent.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }
        if (updateEventUserRequest.getPaid() != null) {
            oldEvent.setPaid(updateEventUserRequest.getPaid());
        }
        if (updateEventUserRequest.getCategory() != null) {
            oldEvent.setCategory(categoryRepository.findById(updateEventUserRequest.getCategory()).orElseThrow(() ->
                    new CategoryNotFoundException("Category not found by id = " + updateEventUserRequest.getCategory())));
        }
        if (updateEventUserRequest.getStateAction() != null) {
            if (updateEventUserRequest.getStateAction().equals(StateActionPrivate.SEND_TO_REVIEW)) {
                oldEvent.setState(EventState.PENDING);
            } else if (updateEventUserRequest.getStateAction().equals(StateActionPrivate.CANCEL_REVIEW)) {
                oldEvent.setState(EventState.CANCELED);
            }
        }

        return oldEvent;
    }

    private void checkEventTitle(NewEventDto newEventDto) {
        if (eventRepository.existsEventByTitle(newEventDto.getTitle())) {
            log.warn("Event with title = " + newEventDto.getTitle() + " already exists");
            throw new EventConflictException("Event with title = " + newEventDto.getTitle() + " already exists");
        }
    }

    private Event buildEventToCreated(Long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found by id = " + userId));

        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() ->
                new CategoryNotFoundException("Category not found by id = " + newEventDto.getCategory()));

        Event eventFromNewEventDto = toEventFromNewEventDto(newEventDto);

        eventFromNewEventDto.setInitiator(user);
        eventFromNewEventDto.setCategory(category);
        eventFromNewEventDto.setCreatedOn(LocalDateTime.now());
        eventFromNewEventDto.setState(EventState.PENDING);
        eventFromNewEventDto.setViews(0L);
        eventFromNewEventDto.setConfirmedRequests(0L);

        if (eventFromNewEventDto.getParticipantLimit() == null) {
            eventFromNewEventDto.setParticipantLimit(0);
        }

        if (eventFromNewEventDto.getRequestModeration() == null) {
            eventFromNewEventDto.setRequestModeration(true);
        }

        if (eventFromNewEventDto.getPaid() == null) {
            eventFromNewEventDto.setPaid(false);
        }

        return eventFromNewEventDto;
    }

    private void checkUserExist(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.warn("User not found by id = {}", userId);
            throw new UserNotFoundException("User not found by id = " + userId);
        }
    }

    private void checkEventDate(LocalDateTime eventDate) {
        if (eventDate.isBefore(now().plusHours(2))) {
            throw new EventDatePatameterException("Event date conflict");
        }
    }
}
