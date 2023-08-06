package ru.practicum.main.request.service;

import ru.practicum.main.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestPrivateService {
    List<ParticipationRequestDto> getAllRequestsByIdUser(Long userId);

    ParticipationRequestDto createRequest(Long userId, Long eventId);
}
