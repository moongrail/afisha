package ru.practicum.main.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.event.exception.EventNotFoundException;
import ru.practicum.main.event.repositories.EventRepository;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.repositories.RequestPrivateRepository;
import ru.practicum.main.user.exception.UserNotFoundException;
import ru.practicum.main.user.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.main.request.mapper.RequestMapperUtil.toParticipationRequestDtoList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RequestPrivateServiceImpl implements RequestPrivateService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestPrivateRepository requestPrivateRepository;

    @Override
    public List<ParticipationRequestDto> getAllRequestsByIdUser(Long userId) {
        checkUserExist(userId);

        List<Request> requests = requestPrivateRepository.findAllByRequesterId(userId);

        if (requests.isEmpty()) {
            return new ArrayList<>();
        }

        return toParticipationRequestDtoList(requests);
    }


    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        checkUserExist(userId);
        checkEventExist(eventId);


        return null;
    }

    private void checkUserExist(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.error("User not found with id = {}", userId);
            throw new UserNotFoundException("User not found with id = " + userId);
        }
    }
    private void checkEventExist(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            log.error("Event not found with id = {}", eventId);
            throw new EventNotFoundException("Event not found with id = " + eventId);
        }
    }
}
