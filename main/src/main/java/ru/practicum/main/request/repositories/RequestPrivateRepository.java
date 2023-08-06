package ru.practicum.main.request.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.request.model.Request;

import java.util.List;

@Repository
public interface RequestPrivateRepository extends JpaRepository<Request,Long> {
    List<Request> findAllByRequesterId(Long requesterId);
}
