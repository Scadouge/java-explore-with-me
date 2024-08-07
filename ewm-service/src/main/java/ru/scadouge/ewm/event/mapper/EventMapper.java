package ru.scadouge.ewm.event.mapper;

import org.mapstruct.*;
import ru.scadouge.ewm.category.model.Category;
import ru.scadouge.ewm.event.args.*;
import ru.scadouge.ewm.event.dto.*;
import ru.scadouge.ewm.event.dto.enums.EventSort;
import ru.scadouge.ewm.event.model.Event;
import ru.scadouge.ewm.location.Location;
import ru.scadouge.ewm.user.model.User;
import ru.scadouge.ewm.utils.TimeHelper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "initiator", source = "user")
    Event toModel(NewEventArgs args, User user, Category category, Location location);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "category", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEvent(@MappingTarget Event event, EventUserUpdateArgs updateArgs);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "category", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEvent(@MappingTarget Event event, EventAdminUpdateArgs updateArgs);

    @Mapping(target = ".", source = "event")
    EventFullDto toEventFullDto(EventWithViewsArgs eventWithViewsArgs);

    List<EventFullDto> toEventFullDto(List<EventWithViewsArgs> eventWithViewsArgs);

    @Mapping(target = ".", source = "event")
    EventShortDto toEventShortDto(EventWithViewsArgs eventWithViewsArgs);

    List<EventShortDto> toEventShortDto(List<EventWithViewsArgs> eventWithViewsArgs);

    // ============= ARGS ============= //

    @Mapping(target = "state", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    NewEventArgs toNewEventArgs(NewEventDto newEventDto);

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    EventUserUpdateArgs toEventUserUpdateArgs(UpdateEventUserRequest updateEventUserRequest);

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    EventAdminUpdateArgs toEventAdminUpdateArgs(UpdateEventAdminRequest updateEventAdminRequest);

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    AdminSearchEventsArgs toAdminSearchEventsArgs(LocalDateTime rangeStart, LocalDateTime rangeEnd, List<Long> users,
                                                  List<String> states, List<Long> categories, int from, int size);

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    SearchEventsArgs toSearchEventsArgs(LocalDateTime rangeStart, LocalDateTime rangeEnd, String text, List<Long> categories,
                                        Boolean paid, Boolean onlyAvailable, EventSort sort, int from, int size);

    default List<EventWithViewsArgs> toEventWithViewsArgs(Map<Long, Event> events, Map<Long, Integer> views) {
        List<EventWithViewsArgs> list = new ArrayList<>();
        for (Map.Entry<Long, Event> entry : events.entrySet()) {
            Event event = entry.getValue();
            long eventViews = 0L;
            if (views.containsKey(entry.getKey())) {
                eventViews = views.get(entry.getKey());
            }
            list.add(EventWithViewsArgs.builder().event(event).views(eventViews).build());
        }
        return list;
    }

    // ============= DATETIME TYPE CONVERSION ============= //

    default Timestamp toTimestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Timestamp.valueOf(localDateTime);
    }

    default String fromTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime().format(TimeHelper.DATE_TIME_FORMATTER);
    }
}
