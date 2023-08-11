package ru.practicum.stat_server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.stat_dto.EndpointHitDto;
import ru.practicum.stat_dto.ViewStatsDto;
import ru.practicum.stat_server.exceptions.StatDateParameterException;
import ru.practicum.stat_server.model.Statistic;
import ru.practicum.stat_server.repositories.StatisticRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.stat_server.mapper.StatisticMapper.fromEndpointHitDto;
import static ru.practicum.stat_server.mapper.StatisticMapper.toEndpointHitDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;

    @Override
    public EndpointHitDto addHit(EndpointHitDto endpointHitDto) {
        Statistic statistic = statisticRepository.save(fromEndpointHitDto(endpointHitDto));
        log.info("Statistic добавлена: {}", statistic);
        return toEndpointHitDto(statistic);
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {

        if (start != null && end != null) {
            if (start.isAfter(end)) {
                throw new StatDateParameterException("Начальная дата должна быть раньше конечной");
            }
        }

        if (unique) {
            if (uris == null) {
                return statisticRepository.getStatsByUniqueWithoutUri(start, end);
            }
            return statisticRepository.getStatsByUnique(start, end, uris);
        } else {
            if (uris == null) {
                return statisticRepository.getStatsByUriWithoutUnique(start, end);
            }
            return statisticRepository.getAllStats(start, end, uris);
        }
    }

    @Override
    public Boolean isUniqueIp(String ip, String uri) {
        Boolean aBoolean = statisticRepository.existsByIpAndUri(ip, uri);
        log.info("isUniqueIp: {}", aBoolean);
        return aBoolean;
    }
}
