package ru.practicum.main.comment.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestUpdateDto {
    @NotEmpty
    @Size(min = 1, max = 1024)
    private String text;
}
