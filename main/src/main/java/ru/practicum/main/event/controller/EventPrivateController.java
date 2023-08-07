package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.service.EventPrivateService;
import ru.practicum.main.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@Slf4j
@Validated
public class EventPrivateController {
    private final EventPrivateService eventPrivateService;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> findAllByUserId(@PathVariable @Positive Long userId,
                                                               @RequestParam(defaultValue = "0")
                                                               @PositiveOrZero Integer from,
                                                               @RequestParam(defaultValue = "10")
                                                               @Positive Integer size) {

        return ResponseEntity
                .ok(eventPrivateService.findAllByUserId(userId, from, size));
    }

    @PostMapping
    public ResponseEntity<EventFullDto> createEvent(@PathVariable @Positive Long userId,
                                                    @RequestBody @Valid NewEventDto newEventDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Bad parameters to create event: {}", newEventDto);
            ResponseEntity.badRequest()
                    .body(newEventDto);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventPrivateService.createEvent(userId, newEventDto));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> findEventByUserIdAndEventId(@PathVariable @Positive Long userId,
                                                                    @PathVariable @Positive Long eventId) {
        return ResponseEntity
                .ok(eventPrivateService.findEventByUserIdAndEventId(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEvent(@PathVariable @Positive Long userId,
                                                    @PathVariable @Positive Long eventId,
                                                    @RequestBody UpdateEventUserRequest updateEventUserRequest) {

        return ResponseEntity
                .ok(eventPrivateService.updateEvent(userId, eventId, updateEventUserRequest));
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> findAllByUserIdAndEventIdRequests(@PathVariable
                                                                                           @Positive Long userId,
                                                                                           @PathVariable
                                                                                           @Positive Long eventId) {
        return ResponseEntity
                .ok(eventPrivateService.findAllByUserIdAndEventIdRequests(userId, eventId));
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> updateEventRequests(@PathVariable @Positive Long userId,
                                                                              @PathVariable @Positive Long eventId,
                                                                              @RequestBody
                                                                              EventRequestStatusUpdateRequest eventStatusUpdateRequest) {

        return ResponseEntity
                .ok(eventPrivateService.updateEventRequests(userId, eventId, eventStatusUpdateRequest));
    }
}
