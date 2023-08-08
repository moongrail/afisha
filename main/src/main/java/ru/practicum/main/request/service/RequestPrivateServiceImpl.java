package ru.practicum.main.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.event.exception.EventNotFoundException;
import ru.practicum.main.event.exception.EventConflictException;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repositories.EventRepository;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.exception.RequestNotFoundException;
import ru.practicum.main.request.exception.RequestConflictException;
import ru.practicum.main.request.mapper.RequestMapperUtil;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.repositories.RequestRepository;
import ru.practicum.main.user.exception.UserNotFoundException;
import ru.practicum.main.user.exception.UserParameterException;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static ru.practicum.main.event.model.EventState.PUBLISHED;
import static ru.practicum.main.request.mapper.RequestMapperUtil.toParticipationRequestDto;
import static ru.practicum.main.request.mapper.RequestMapperUtil.toParticipationRequestDtoList;
import static ru.practicum.main.request.model.RequestStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RequestPrivateServiceImpl implements RequestPrivateService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Override
    public List<ParticipationRequestDto> getAllRequestsByIdUser(Long userId) {
        checkUserExist(userId);

        List<Request> requests = requestRepository.findAllByRequesterId(userId);

        if (requests.isEmpty()) {
            return new ArrayList<>();
        }

        return toParticipationRequestDtoList(requests);
    }


    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        if (!userRepository.existsById(userId)){
            throw new UserParameterException("User not found by id = " + userId);
        }

        User requester = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Event event = eventRepository
                .findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found"));

        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            log.warn("Request already exists for user {} and event {}", userId, eventId);
            throw new RequestConflictException("Request already exists");
        }

        if (eventRepository.existsByInitiatorIdAndId(userId, eventId)) {
            log.warn("Not owner event for user {} and event {}", userId, eventId);
            throw new EventConflictException("Not owner event");
        }

        checkStateEvent(event);
        checkParticipantEvent(event);

        Request buildRequest = getSaveRequest(eventId, requester, event);

        log.info("Request created for user {} and event {}", userId, eventId);
        return RequestMapperUtil.toParticipationRequestDto(requestRepository.save(buildRequest));
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        checkUserExist(userId);

        Request request = requestRepository.findById(requestId).orElseThrow(() ->
                new RequestNotFoundException("Request not found requestId = " + requestId));

        if (!request.getRequester().getId().equals(userId)) {
            log.warn("Not owner request for user {} and request {}", userId, requestId);
            throw new RequestConflictException("Not owner request");
        }

        request.setStatus(CANCELED);
        Request updatedRequest = requestRepository.save(request);

        Event event = eventRepository.findById(updatedRequest.getEvent().getId())
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        if (event.getConfirmedRequests() > 0) {
            eventRepository.setEventConfirmedRequests(updatedRequest.getEvent().getId(),
                            event.getConfirmedRequests() - 1);
        }

        return toParticipationRequestDto(updatedRequest);
    }

    private Request getSaveRequest(Long eventId, User requester, Event event) {
        Request saveRequest = Request.builder()
                .requester(requester)
                .event(event)
                .created(now())
                .build();

        if (event.getRequestModeration() && event.getParticipantLimit() != 0) {
            saveRequest.setStatus(PENDING);
        } else {
            saveRequest.setStatus(CONFIRMED);
            eventRepository.setEventConfirmedRequests(eventId, event.getConfirmedRequests() + 1);
        }

        return saveRequest;
    }

    private static void checkParticipantEvent(Event event) {
        if (event.getParticipantLimit() != 0
                && (event.getParticipantLimit() - event.getConfirmedRequests()) <= 0) {
            log.warn("Participant limit reached");
            throw new EventConflictException("Participant limit reached");
        }
    }

    private static void checkStateEvent(Event event) {
        if (!event.getState().equals(PUBLISHED)){
            log.warn("Event not published");
            throw new EventConflictException("Event not published");
        }
    }

    private void checkUserExist(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.warn("User not found with id = {}", userId);
            throw new UserNotFoundException("User not found with id = " + userId);
        }
    }
//    private void checkEventExist(Long eventId) {
//        if (!eventRepository.existsById(eventId)) {
//            log.error("Event not found with id = {}", eventId);
//            throw new EventNotFoundException("Event not found with id = " + eventId);
//        }
//    }
}
