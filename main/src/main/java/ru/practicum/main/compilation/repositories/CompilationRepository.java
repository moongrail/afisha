package ru.practicum.main.compilation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.compilation.model.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation,Long> {
    boolean existsByTitle(String title);
}
