package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentRequestCreateDto;
import ru.practicum.main.comment.service.CommentPrivateService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/users/{userId}/events/{eventId}/comments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentPrivateController {
  private CommentPrivateService commentPrivateService;

  @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable @Positive Long userId,
                                                    @PathVariable @Positive Long eventId,
                                                    @RequestBody @Valid CommentRequestCreateDto requestCreateDto){

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentPrivateService.createComment(userId,eventId,requestCreateDto));
  }
}
