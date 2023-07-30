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
        List<ViewStatsDto> viewStats = new ArrayList<>();
        if (unique) {
            if (uris.length == 0) {
                viewStats = statisticRepository.getStatsByUniqueWithoutUri(start, end);
            }
            viewStats = statisticRepository.getStatsByUnique(start, end, uris);
        } else {
            if (uris.length == 0) {
                viewStats = statisticRepository.getStatsByUriWithoutUri(start, end);
            }
            viewStats = statisticRepository.getAllStats(start, end, uris);
        }
//        for (Object[] data : statsData) {
//            String app = (String) data[0];
//            String uri = (String) data[1];
//            Integer hits = (Integer) data[2];
//            ViewStatsDto viewStatsDto = toResponseView(app, uri, hits);
//
//            viewStats.add(viewStatsDto);
//        }

        return viewStats;
    }
}
