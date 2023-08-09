package ru.practicum.main.compilation.dto;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
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
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
    private Boolean pinned;
    @UniqueElements
    private List<Long> events;

}
