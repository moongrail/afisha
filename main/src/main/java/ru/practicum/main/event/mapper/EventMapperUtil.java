package ru.practicum.main.event.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.practicum.main.category.mapper.CategoryMapperUtil;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.mapper.UserMapperUtil;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.main.category.mapper.CategoryMapperUtil.*;
import static ru.practicum.main.user.mapper.UserMapperUtil.*;

@UtilityClass
public class EventMapperUtil {
    public static EventFullDto toEventFullDto(Event updatedEvent) {
        return EventFullDto.builder()
                .id(updatedEvent.getId())
                .annotation(updatedEvent.getAnnotation())
                .category(updatedEvent.getCategory())
                .confirmedRequests(updatedEvent.getConfirmedRequests())
                .createdOn(updatedEvent.getCreatedOn())
                .description(updatedEvent.getDescription())
                .eventDate(updatedEvent.getEventDate())
                .initiator(updatedEvent.getInitiator())
                .location(updatedEvent.getLocation())
                .paid(updatedEvent.getPaid())
                .participantLimit(updatedEvent.getParticipantLimit())
                .publishedOn(updatedEvent.getPublishedOn())
                .requestModeration(updatedEvent.getRequestModeration())
                .state(updatedEvent.getState())
                .title(updatedEvent.getTitle())
                .views(updatedEvent.getViews())
                .build();
    }

    public static List<EventFullDto> toEventFullDtoList(Page<Event> eventPage) {
        return  eventPage.getContent().stream()
                .map(EventMapperUtil::toEventFullDto)
                .collect(Collectors.toList());
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }
}
