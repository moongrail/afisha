package ru.practicum.main.event.dto;

import lombok.*;
import ru.practicum.main.event.model.EventState;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRequestStatusUpdateRequest {
    List<Long> ids;
    private EventState status;
}
