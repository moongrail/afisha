package ru.practicum.main.compilation.service;

import ru.practicum.main.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationPublicService {
    List<CompilationDto> findAllCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto findCompilationById(Long compId);
}
