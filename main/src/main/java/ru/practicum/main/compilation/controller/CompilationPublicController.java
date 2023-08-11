package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.service.CompilationPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Validated
public class CompilationPublicController {
    private final CompilationPublicService compilationPublicService;

    @GetMapping
    public ResponseEntity<List<CompilationDto>> findAllCompilations(@RequestParam(required = false) Boolean pinned,
                                                                    @RequestParam(defaultValue = "0")
                                                                    @PositiveOrZero Integer from,
                                                                    @RequestParam(defaultValue = "10")
                                                                    @Positive Integer size) {

        return ResponseEntity.ok(compilationPublicService.findAllCompilations(pinned, from, size));
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> findCompilationById(@PathVariable @Positive Long compId) {

        return ResponseEntity.ok(compilationPublicService.findCompilationById(compId));
    }
}
