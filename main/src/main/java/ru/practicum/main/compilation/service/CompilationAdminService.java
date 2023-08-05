package ru.practicum.main.compilation.service;

import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;

public interface CompilationAdminService {

    CompilationDto addCompilation(NewCompilationDto request);
}
