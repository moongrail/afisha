package ru.practicum.main.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.model.Compilation;

@UtilityClass
public class CompilationMapperUtil {

    public static Compilation fromDto(NewCompilationDto newCompilationDto){
        return Compilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.isPinned())
                .build();
    }
}
