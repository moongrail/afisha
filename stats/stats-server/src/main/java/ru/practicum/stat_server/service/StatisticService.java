package ru.practicum.stat_server.service;

import ru.practicum.stat_dto.EndpointHitDto;
import ru.practicum.stat_dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    EndpointHitDto addHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}
