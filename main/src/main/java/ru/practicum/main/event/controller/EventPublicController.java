package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.EventTypeSort;
import ru.practicum.main.event.service.EventPublicService;
import ru.practicum.stat_client.EndpointHitClient;
import ru.practicum.stat_client.ViewStatisticClient;
import ru.practicum.stat_dto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Slf4j
@Validated
public class EventPublicController {
    private final EventPublicService eventPublicService;
    private final EndpointHitClient endpointHitClient;
    private final ViewStatisticClient viewStatisticClient;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> findAllEvents(@RequestParam(required = false) String text,
                                                             @RequestParam(required = false) Long[] categories,
                                                             @RequestParam(required = false) Boolean paid,
                                                             @RequestParam(required = false) LocalDateTime rangeStart,
                                                             @RequestParam(required = false) LocalDateTime rangeEnd,
                                                             @RequestParam(required = false, defaultValue = "false")
                                                             Boolean onlyAvailable,
                                                             @RequestParam(required = false, defaultValue = "EVENT_DATE")
                                                             EventTypeSort sort,
                                                             @RequestParam(defaultValue = "0")
                                                             @PositiveOrZero Integer from,
                                                             @RequestParam(defaultValue = "10")
                                                             @Positive Integer size,
                                                             HttpServletRequest request) {

        sendStatisticHit(request);

        return ResponseEntity
                .ok(eventPublicService.findAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                        sort, from, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventFullDto> findEventById(@PathVariable @Positive Long id, HttpServletRequest request) {
        Boolean existIp = (Boolean) viewStatisticClient
                .getIsUniqueIp(request.getRemoteAddr(),request.getRequestURI()).getBody();

        log.info("IP: {}", existIp);
        sendStatisticHit(request);
        return ResponseEntity.ok(eventPublicService.findEventById(id, existIp));
    }


    private void sendStatisticHit(HttpServletRequest request) {
        endpointHitClient.addHit(EndpointHitDto
                .builder()
                .app("main-service")
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .ip(request.getRemoteAddr())
                .build());
    }

}
