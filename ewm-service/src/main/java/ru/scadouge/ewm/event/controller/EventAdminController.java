package ru.scadouge.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.scadouge.ewm.error.BadRequestException;
import ru.scadouge.ewm.event.args.AdminSearchEventsArgs;
import ru.scadouge.ewm.event.args.EventWithViewsArgs;
import ru.scadouge.ewm.event.dto.EventFullDto;
import ru.scadouge.ewm.event.dto.UpdateEventAdminRequest;
import ru.scadouge.ewm.event.mapper.EventMapper;
import ru.scadouge.ewm.event.service.EventService;
import ru.scadouge.ewm.event.validation.ValidSearchDateInterval;
import ru.scadouge.ewm.utils.TimeHelper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@Slf4j
@Validated
@RequiredArgsConstructor
public class EventAdminController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    @ValidSearchDateInterval
    public List<EventFullDto> getEvents(@RequestParam(required = false)
                                        @DateTimeFormat(pattern = TimeHelper.DATE_TIME_PATTERN) LocalDateTime rangeStart,
                                        @RequestParam(required = false)
                                        @DateTimeFormat(pattern = TimeHelper.DATE_TIME_PATTERN) LocalDateTime rangeEnd,
                                        @RequestParam(required = false) Double lat,
                                        @RequestParam(required = false) Double lon,
                                        @RequestParam(required = false) Double radius,
                                        @RequestParam(required = false) List<Long> users,
                                        @RequestParam(required = false) List<String> states,
                                        @RequestParam(required = false) List<Long> categories,
                                        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(defaultValue = "10") @Positive Integer size) {
        if (lat != null || lon != null || radius != null) {
            if (lat == null || lon == null || radius == null) {
                throw new BadRequestException("Параметры lat, lon и radius должны быть инициализированны вместе " +
                        "или отсутствовать вообще.");
            }
        }
        AdminSearchEventsArgs args = eventMapper.toAdminSearchEventsArgs(rangeStart, rangeEnd, users, states, categories,
                lat, lon, radius, from, size);
        log.info("ADMIN: Получение списка всех событий args={}", args);
        List<EventWithViewsArgs> events = eventService.getAllEventsByAdmin(args);
        return eventMapper.toEventFullDto(events);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable long eventId,
                                   @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("ADMIN: Обновление события eventId={}, updateEventAdminRequest={}",
                eventId, updateEventAdminRequest);
        EventWithViewsArgs event = eventService.updateEventByAdmin(eventId,
                eventMapper.toEventAdminUpdateArgs(updateEventAdminRequest));
        return eventMapper.toEventFullDto(event);
    }
}
