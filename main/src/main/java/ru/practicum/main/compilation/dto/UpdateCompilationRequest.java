package ru.practicum.main.compilation.dto;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCompilationRequest {
    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;
    private boolean pinned;
    @UniqueElements
    private List<Long> events;
}
