package ru.practicum.main.event.dto;

import lombok.*;
import ru.practicum.main.request.model.RequestStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRequestStatusUpdateRequest {
    Long[] requestIds;
    private RequestStatus status;
}
