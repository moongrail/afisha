package ru.practicum.stat_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.stat_server.model.Statistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    @Query("SELECT s.app, s.uri, COUNT(DISTINCT s.ip)  " +
            "FROM stat s " +
            "WHERE s.created BETWEEN :start AND :end " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(DISTINCT s.ip) DESC")
    List<Object[]> getStatsByUniqueWithoutUri(LocalDateTime start, LocalDateTime end);

    @Query("SELECT s.app, s.uri, COUNT(DISTINCT s.ip) " +
            "FROM stat s " +
            "WHERE s.created BETWEEN :start AND :end " +
            "AND s.uri IN (:uris) " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(DISTINCT s.ip) DESC")
    List<Object[]> getStatsByUnique(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query("SELECT s.app, s.uri, COUNT(s.ip) " +
            "FROM stat s " +
            "WHERE s.created BETWEEN :start AND :end " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(s.ip) DESC")
    List<Object[]> getStatsByUriWithoutUnique(LocalDateTime start, LocalDateTime end);

    @Query("SELECT s.app, s.uri, COUNT(s.ip) " +
            "FROM stat s " +
            "WHERE s.created BETWEEN :start AND :end " +
            "AND s.uri IN :uris " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(s.ip) DESC")
    List<Object[]> getAllStats(LocalDateTime start,
                               LocalDateTime end,
                               String[] uris);
}
