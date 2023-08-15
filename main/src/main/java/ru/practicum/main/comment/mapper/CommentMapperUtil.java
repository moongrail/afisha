package ru.practicum.main.comment.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentRequestCreateDto;
import ru.practicum.main.comment.model.Comment;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@UtilityClass
public class CommentMapperUtil {

    public static Comment fromCreateRequestDro(CommentRequestCreateDto requestCreateDto) {
        return Comment.builder()
                .text(requestCreateDto.getText())
                .created(now())
                .build();
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .actor(comment.getActor())
                .created(comment.getCreated())
                .modified(comment.getModified())
                .build();
    }
}
