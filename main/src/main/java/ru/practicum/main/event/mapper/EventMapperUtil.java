package ru.practicum.main.event.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.main.category.mapper.CategoryMapperUtil.toCategoryDto;
import static ru.practicum.main.user.mapper.UserMapperUtil.toUserShortDto;

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
        return eventPage.getContent().stream()
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

    public static List<EventShortDto> toEventShortDtoList(List<Event> events) {
        return events.stream()
                .map(EventMapperUtil::toEventShortDto)
                .collect(Collectors.toList());
    }

    public static Event toEventFromNewEventDto(NewEventDto newEventDto) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .paid(newEventDto.getPaid())
                .eventDate(newEventDto.getEventDate())
                .location(newEventDto.getLocation())
                .title(newEventDto.getTitle())
                .build();
    }
}
