package ru.practicum.main.event.service;

import ru.practicum.main.event.dto.EventAdminPatch;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventAdminService {
    EventFullDto patchEvent(Long eventId, EventAdminPatch request);

    List<EventFullDto> findAllEvents(Long[] users, EventState[] states, Long[] categories,
                                     LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);
}
