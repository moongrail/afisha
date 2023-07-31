package ru.practicum.stat_server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.stat_dto.EndpointHitDto;
import ru.practicum.stat_dto.ViewStatsDto;
import ru.practicum.stat_server.model.Statistic;
import ru.practicum.stat_server.repositories.StatisticRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.stat_server.mapper.StatisticMapper.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;

    @Override
    public EndpointHitDto addHit(EndpointHitDto endpointHitDto) {
        Statistic statistic = fromEndpointHitDto(endpointHitDto);
        log.info("Statistic добавлена: {}", statistic);
        return toEndpointHitDto(statisticRepository.save(statistic));
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {

        if (unique) {
            if (uris == null) {
                return convertToViewStatDto(statisticRepository.getStatsByUniqueWithoutUri(start, end));
            }
            return convertToViewStatDto(statisticRepository.getStatsByUnique(start, end, uris));
        } else {
            if (uris == null) {
                return convertToViewStatDto(statisticRepository.getStatsByUriWithoutUnique(start, end));
            }
            return convertToViewStatDto(statisticRepository.getAllStats(start, end, uris));
        }
    }

    private static List<ViewStatsDto> convertToViewStatDto(List<Object[]> statsData) {
        List<ViewStatsDto> viewStats = new ArrayList<>();

        for (Object[] data : statsData) {
            String app = (String) data[0];
            String uri = (String) data[1];
            Long hits = (Long) data[2];
            ViewStatsDto viewStatsDto = toResponseView(app, uri, hits);

            viewStats.add(viewStatsDto);
        }

        return viewStats;
    }
}
