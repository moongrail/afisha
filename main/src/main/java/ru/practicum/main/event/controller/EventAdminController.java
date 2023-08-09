package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.UpdateEventAdminRequest;
import ru.practicum.main.event.model.EventState;
import ru.practicum.main.event.service.EventAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
@Slf4j
@Validated
public class EventAdminController {
    private final EventAdminService eventAdminService;

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> patchEvent(@PathVariable @Positive Long eventId,
                                                   @RequestBody @Valid UpdateEventAdminRequest request) {

        return ResponseEntity.ok(eventAdminService.patchEvent(eventId, request));
    }

    @GetMapping
    public ResponseEntity<List<EventFullDto>> findAll(@RequestParam(required = false) Long[] users,
                                                      @RequestParam(required = false) EventState[] states,
                                                      @RequestParam(required = false) Long[] categories,
                                                      @RequestParam(required = false)
                                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                      LocalDateTime rangeStart,
                                                      @RequestParam(required = false)
                                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                      LocalDateTime rangeEnd,
                                                      @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                      @RequestParam(defaultValue = "10") @Positive Integer size) {
        return ResponseEntity
                .ok(eventAdminService.findAllEvents(users, states, categories, rangeStart, rangeEnd, from, size));
    }

}
