package ru.practicum.main.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.model.Request;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RequestMapperUtil {
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

    public static List<ParticipationRequestDto> toParticipationRequestDtoList(List<Request> requests) {
        return requests.stream()
                .map(RequestMapperUtil::toParticipationRequestDto)
                .collect(Collectors.toList());
    }
}
