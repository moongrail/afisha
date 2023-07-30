package ru.practicum.stat_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.stat_dto.ViewStatsDto;
import ru.practicum.stat_server.model.Statistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {


    List<ViewStatsDto> getStatsByUniqueWithoutUri(LocalDateTime start, LocalDateTime end);

    List<ViewStatsDto> getStatsByUnique(LocalDateTime start, LocalDateTime end, String[] uris);

    List<ViewStatsDto> getStatsByUriWithoutUri(LocalDateTime start, LocalDateTime end);

    List<ViewStatsDto> getAllStats(LocalDateTime start, LocalDateTime end, String[] uris);
}
