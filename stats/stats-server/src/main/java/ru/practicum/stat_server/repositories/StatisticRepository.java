package ru.practicum.stat_server.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.stat_dto.ViewStatsDto;
import ru.practicum.stat_server.model.Statistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface StatisticRepository extends CrudRepository<Statistic, Long> {

    @Query("SELECT new ru.practicum.stat_dto.ViewStatsDto(s.app, s.uri, COUNT(DISTINCT s.ip)) " +
            "FROM stat s " +
            "WHERE s.created BETWEEN :start AND :end " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(DISTINCT s.ip) DESC")
    List<ViewStatsDto> getStatsByUniqueWithoutUri(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.stat_dto.ViewStatsDto(s.app, s.uri, COUNT(DISTINCT s.ip)) " +
            "FROM stat s " +
            "WHERE s.created BETWEEN :start AND :end " +
            "AND s.uri IN (:uris) " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(DISTINCT s.ip) DESC")
    List<ViewStatsDto> getStatsByUnique(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query("SELECT new ru.practicum.stat_dto.ViewStatsDto(s.app, s.uri, COUNT(s.ip)) " +
            "FROM stat s " +
            "WHERE s.created BETWEEN :start AND :end " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(s.ip) DESC")
    List<ViewStatsDto> getStatsByUriWithoutUnique(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.stat_dto.ViewStatsDto(s.app, s.uri, COUNT(s.ip)) " +
            "FROM stat s " +
            "WHERE s.created BETWEEN :start AND :end " +
            "AND s.uri IN :uris " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(s.ip) DESC")
    List<ViewStatsDto> getAllStats(LocalDateTime start,
                                   LocalDateTime end,
                                   String[] uris);

    Boolean existsByIpAndUri(String ip, String uri);
}
