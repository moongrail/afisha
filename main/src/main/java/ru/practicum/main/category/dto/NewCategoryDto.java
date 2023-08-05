package ru.practicum.main.category.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    @NotEmpty
    private String name;
}
