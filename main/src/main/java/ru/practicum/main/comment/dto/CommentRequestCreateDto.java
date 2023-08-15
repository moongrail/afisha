package ru.practicum.main.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.main.user.model.User;

import java.time.LocalDateTime;

public class CommentRequestCreateDto {
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

}
