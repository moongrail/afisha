package ru.practicum.main.compilation.dto;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCompilationDto {
    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;
    private boolean pinned;
    @UniqueElements
    private List<Long> events;

}
