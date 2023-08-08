package ru.practicum.main.event.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.main.event.model.Event;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.event.model.EventState;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface EventRepository extends JpaRepository<Event,Long> {
    Page<Event> findAll(Specification<Event> specification, Pageable pageable);
    boolean existsByInitiatorIdAndId(Long initiatorId, Long id);
    Optional<Event> findEventByInitiatorIdAndId(Long userId, Long eventId);
    @Modifying
    @Query(value = "UPDATE events " +
            "SET confirmed_requests = :requests " +
            "WHERE id = :id")
    void setEventConfirmedRequests(@Param("id") Long eventId, @Param("requests") Long confirmedRequests);

    List<Event> findEventsByInitiatorId(Long userId, Pageable pageable);

    boolean existsEventByTitle(String title);

    Optional<Event> findByIdAndState(Long id, EventState state);
}
