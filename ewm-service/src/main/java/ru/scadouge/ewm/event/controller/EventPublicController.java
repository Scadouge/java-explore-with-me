package ru.scadouge.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.scadouge.ewm.event.args.EventWithViewsArgs;
import ru.scadouge.ewm.event.args.SearchEventsArgs;
import ru.scadouge.ewm.event.dto.EventFullDto;
import ru.scadouge.ewm.event.dto.enums.EventSort;
import ru.scadouge.ewm.event.mapper.EventMapper;
import ru.scadouge.ewm.event.service.EventService;
import ru.scadouge.ewm.event.validation.ValidSearchDateInterval;
import ru.scadouge.ewm.utils.TimeHelper;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@Slf4j
@Validated
@RequiredArgsConstructor
public class EventPublicController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    @ValidSearchDateInterval
    public List<EventFullDto> getEvents(@RequestParam(required = false)
                                        @DateTimeFormat(pattern = TimeHelper.DATE_TIME_PATTERN) LocalDateTime rangeStart,
                                        @RequestParam(required = false)
                                        @DateTimeFormat(pattern = TimeHelper.DATE_TIME_PATTERN) LocalDateTime rangeEnd,
                                        @RequestParam(required = false) String text,
                                        @RequestParam(required = false) List<Long> categories,
                                        @RequestParam(required = false) Boolean paid,
                                        @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                        @RequestParam(required = false) EventSort sort,
                                        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(defaultValue = "10") @Positive Integer size) {
        SearchEventsArgs args = eventMapper.toSearchEventsArgs(rangeStart, rangeEnd, text, categories,
                paid, onlyAvailable, sort, from, size);
        if (rangeStart == null && rangeEnd == null) {
            args = args.toBuilder().rangeStart(Timestamp.valueOf(LocalDateTime.now())).build();
        }
        log.info("PUBLIC: Получение списка всех событий args={}", args);
        List<EventWithViewsArgs> events = eventService.getAllPublishedEventsByPublic(args);
        return eventMapper.toEventFullDto(events);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable long eventId) {
        log.info("PUBLIC: Получение события eventId={}", eventId);
        EventWithViewsArgs event = eventService.getEventByPublic(eventId);
        return eventMapper.toEventFullDto(event);
    }
}
