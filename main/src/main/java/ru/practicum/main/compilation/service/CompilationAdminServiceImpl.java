package ru.practicum.main.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main.compilation.exception.CompilationNotFoundException;
import ru.practicum.main.compilation.exception.CompilationUniqueTitleException;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.compilation.repositories.CompilationRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repositories.EventRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.main.compilation.mapper.CompilationMapperUtil.toCompilationDto;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompilationAdminServiceImpl implements CompilationAdminService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto addCompilation(NewCompilationDto request) {
        if (compilationRepository.existsByTitle(request.getTitle())) {
            throw new CompilationUniqueTitleException("Title must be unique");
        }

        List<Event> events = new ArrayList<>();
        if (request.getEvents() != null && request.getEvents().size() != 0) {
            events = eventRepository.findAllById(request.getEvents());
        }

        Compilation compilation = Compilation.builder()
                .pinned(request.getPinned() != null && request.getPinned())
                .title(request.getTitle())
                .events(events)
                .build();

        return toCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Long compId) {
        if (!compilationRepository.existsById(compId)) {
            log.error("Compilation not found by id {}", compId);
            throw new CompilationNotFoundException("Compilation not found by id " + compId);
        }

        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto patchCompilation(Long compId, UpdateCompilationRequest request) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new CompilationNotFoundException("Compilation not found by id " + compId));

        if (request.getTitle() != null) {
            compilation.setTitle(request.getTitle());
        }
        if (request.getPinned() != null) {
            compilation.setPinned(request.getPinned());
        }
        if (request.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(request.getEvents());
            compilation.setEvents(events);
        }

        Compilation updatedCompilation = compilationRepository.save(compilation);

        return toCompilationDto(updatedCompilation);
    }
}
