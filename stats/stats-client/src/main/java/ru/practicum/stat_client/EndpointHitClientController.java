package ru.practicum.stat_client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.stat_dto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(path = "/hit")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EndpointHitClientController {
    private final EndpointHitClient endpointHitClient;

    @PostMapping
    public ResponseEntity<Object> addHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {

        log.info("addHit: {}", endpointHitDto);
        return endpointHitClient.addHit(endpointHitDto);
    }
}
