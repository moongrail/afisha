package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentFullDto;
import ru.practicum.main.comment.dto.CommentRequestCreateDto;
import ru.practicum.main.comment.dto.CommentRequestUpdateDto;
import ru.practicum.main.comment.service.CommentPrivateService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events/{eventId}/comments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentPrivateController {
    private final CommentPrivateService commentPrivateService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable @Positive Long userId,
                                                    @PathVariable @Positive Long eventId,
                                                    @RequestBody @Valid CommentRequestCreateDto requestCreateDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentPrivateService.createComment(userId, eventId, requestCreateDto));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDto> patchComment(@PathVariable @Positive Long userId,
                                                   @PathVariable @Positive Long eventId,
                                                   @PathVariable @Positive Long commentId,
                                                   @RequestBody @Valid CommentRequestUpdateDto requestUpdateDto) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentPrivateService.patchComment(userId, eventId, commentId, requestUpdateDto));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable @Positive Long userId,
                                              @PathVariable @Positive Long eventId,
                                              @PathVariable @Positive Long commentId) {
        commentPrivateService.deleteComment(userId, eventId, commentId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentFullDto> findCommentByUserIdAndEventId(@PathVariable @Positive Long userId,
                                                                        @PathVariable @Positive Long eventId,
                                                                        @PathVariable @Positive Long commentId) {
        return ResponseEntity
                .ok(commentPrivateService.findCommentByUserIdAndEventId(userId, eventId, commentId));
    }

    @GetMapping
    public ResponseEntity<List<CommentFullDto>> findAllCommentsByUserIdAndEventId(@PathVariable @Positive Long userId,
                                                                                  @PathVariable @Positive Long eventId,
                                                                                  @RequestParam(defaultValue = "0")
                                                                                  @PositiveOrZero Integer from,
                                                                                  @RequestParam(defaultValue = "10")
                                                                                  @Positive Integer size) {

        return ResponseEntity
                .ok(commentPrivateService.findAllCommentsByUserIdAndEventId(userId, eventId, from, size));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommentFullDto>> searchCommentsByUserIdAndEventIdAndText(@PathVariable @Positive Long userId,
                                                                                        @PathVariable @Positive Long eventId,
                                                                                        @RequestParam
                                                                                        @NotBlank @NotEmpty String text,
                                                                                        @RequestParam(defaultValue = "0")
                                                                                        @PositiveOrZero Integer from,
                                                                                        @RequestParam(defaultValue = "10")
                                                                                        @Positive Integer size) {
        return ResponseEntity
                .ok(commentPrivateService.searchCommentsByUserIdAndEventIdAndText(userId, eventId, text, from, size));
    }
}
