package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.service.CompilationAdminService;

import javax.validation.Valid;

@RestController("/admin/compilations")
@Slf4j
@RequiredArgsConstructor
public class CompilationAdminController {
    private final CompilationAdminService compilationAdminService;

    @PostMapping
    public ResponseEntity<CompilationDto> addCompilation(@RequestBody @Valid NewCompilationDto request,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Bad parameters to add compilation: {}", request);
            ResponseEntity.badRequest()
                    .body(request);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(compilationAdminService.addCompilation(request));
    }
}
