package ru.practicum.main.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.exception.CompilationUniqueTitleException;
import ru.practicum.main.compilation.repositories.CompilationRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationAdminServiceImpl implements CompilationAdminService {
    private final CompilationRepository compilationRepository;

    @Override
    public CompilationDto addCompilation(NewCompilationDto request) {
        if (compilationRepository.existsByTitle(request.getTitle())){
            throw new CompilationUniqueTitleException("Title must be unique");
        }


        return null;
    }
}
