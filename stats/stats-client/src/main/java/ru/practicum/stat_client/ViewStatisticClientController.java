package ru.practicum.stat_client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping(path = "/stats")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ViewStatisticClientController {
    private final ViewStatisticClient viewStatisticClient;

    @GetMapping
    public ResponseEntity<Object> viewStatisticClient(@RequestParam(name = "start") LocalDateTime start,
                                                      @RequestParam(name = "end") LocalDateTime end,
                                                      @RequestParam(name = "uris") String[] uris,
                                                      @RequestParam(name = "unique", defaultValue = "false")
                                                      boolean unique) {

        return viewStatisticClient.getViewStatistic(start, end, uris, unique);
    }
}
