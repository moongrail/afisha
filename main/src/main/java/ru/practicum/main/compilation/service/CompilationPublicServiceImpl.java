package ru.practicum.main.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.main.common.util.PaginationUtil;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.exception.CompilationNotFoundException;
import ru.practicum.main.compilation.mapper.CompilationMapperUtil;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.compilation.repositories.CompilationRepository;

import java.util.List;

import static ru.practicum.main.common.util.PaginationUtil.getPaginationAsc;
import static ru.practicum.main.compilation.mapper.CompilationMapperUtil.toCompilationDto;
import static ru.practicum.main.compilation.mapper.CompilationMapperUtil.toCompilationDtoList;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationPublicServiceImpl implements CompilationPublicService {
    private final CompilationRepository compilationRepository;

    @Override
    public List<CompilationDto> findAllCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from,size, Sort.Direction.ASC, "id");

        List<Compilation> compilations;

        if (pinned == null) {
            compilations = compilationRepository.findAll(pageable).getContent();
        } else {
            compilations = compilationRepository.findByPinned(pinned, pageable);
        }

        log.info("findAllCompilations: {}", compilations);
        return toCompilationDtoList(compilations);
    }

    @Override
    public CompilationDto findCompilationById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException("Compilation not found"));

        log.info("findCompilationById: {}", compilation);
        return toCompilationDto(compilation);
    }
}
