package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentFullDto;
import ru.practicum.main.comment.service.CommentAdminService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentAdminController {
    private final CommentAdminService commentAdminService;

    @GetMapping
    public ResponseEntity<List<CommentFullDto>> findAll(@RequestParam(required = false) Long[] users,
                                                        @RequestParam(required = false) Long[] events,
                                                        @RequestParam(required = false)
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                        LocalDateTime rangeStart,
                                                        @RequestParam(required = false)
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                        LocalDateTime rangeEnd,
                                                        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                        @RequestParam(defaultValue = "10") @Positive Integer size) {

        return ResponseEntity.ok(commentAdminService.findAll(users, events, rangeStart, rangeEnd, from, size));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable @Positive Long commentId) {
        commentAdminService.deleteComment(commentId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
