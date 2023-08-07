package ru.practicum.main.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.service.RequestPrivateService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/{userId}/requests")
@Validated
public class RequestPrivateController {
    private final RequestPrivateService requestPrivateService;

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> getAllRequestsByIdUser(@PathVariable @Positive Long userId) {
        return ResponseEntity.ok(requestPrivateService.getAllRequestsByIdUser(userId));
    }

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> createRequest(@PathVariable @Positive Long userId,
                                                                 @RequestParam @Positive Long eventId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(requestPrivateService.createRequest(userId, eventId));

    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequest(@PathVariable @Positive Long userId,
                                                                 @PathVariable @Positive Long requestId){
        return ResponseEntity.ok(requestPrivateService.cancelRequest(userId, requestId));
    }
}
