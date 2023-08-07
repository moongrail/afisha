package ru.practicum.main.request.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.model.RequestStatus;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {
    List<Request> findAllByRequesterId(Long requesterId);

    boolean existsByRequesterIdAndEventId(Long userId, Long eventId);

    List<Request> findRequestsByRequester_IdAndEvent_Id(Long userId, Long eventId);

    List<Request> findRequestsByEvent_IdAndStatus(Long eventId, RequestStatus status);

    List<Request> findRequestsByEvent_IdAndIdIn(Long eventId, List<Long> requestIds);

    void deleteRequestsByEvent_IdAndStatus(Long eventId, RequestStatus status);
}