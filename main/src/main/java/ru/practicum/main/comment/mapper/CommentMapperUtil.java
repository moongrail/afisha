package ru.practicum.main.comment.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentFullDto;
import ru.practicum.main.comment.dto.CommentRequestCreateDto;
import ru.practicum.main.comment.model.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<CommentDto> toCommentDtoList(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapperUtil::toCommentDto)
                .collect(Collectors.toList());
    }

    public static CommentFullDto toCommentFullDto(Comment comment) {
        return CommentFullDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .actor(comment.getActor())
                .eventId(comment.getEvent().getId())
                .created(comment.getCreated())
                .modified(comment.getModified())
                .build();
    }

    public  static List<CommentFullDto> toCommentFullDtoList(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapperUtil::toCommentFullDto)
                .collect(Collectors.toList());
    }
}
