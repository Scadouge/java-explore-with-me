package ru.scadouge.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.scadouge.ewm.event.args.AdminSearchEventsArgs;
import ru.scadouge.ewm.event.args.EventWithViewsArgs;
import ru.scadouge.ewm.event.dto.EventFullDto;
import ru.scadouge.ewm.event.dto.UpdateEventAdminRequest;
import ru.scadouge.ewm.event.mapper.EventMapper;
import ru.scadouge.ewm.event.service.EventService;
import ru.scadouge.ewm.event.validation.ValidSearchDateInterval;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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
    public List<EventFullDto> getEvents(@RequestParam(required = false) String rangeStart,
                                        @RequestParam(required = false) String rangeEnd,
                                        @RequestParam(required = false) List<Long> users,
                                        @RequestParam(required = false) List<String> states,
                                        @RequestParam(required = false) List<Long> categories,
                                        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(defaultValue = "10") @Positive Integer size) {
        AdminSearchEventsArgs args = eventMapper.toAdminSearchEventsArgs(rangeStart, rangeEnd, users, states, categories,
                from, size);
        log.info("ADMIN: Получение списка всех событий args={}", args);
        List<EventWithViewsArgs> events = eventService.getAllEventsByAdmin(args);
        return eventMapper.toEventFullDto(events);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable long eventId,
                                   @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("ADMIN: Обновление события eventId={}, updateEventAdminRequest={}",
                eventId, updateEventAdminRequest);
        EventWithViewsArgs event = eventService.updateEventByAdmin(eventId, eventMapper.toEventAdminUpdateArgs(updateEventAdminRequest));
        return eventMapper.toEventFullDto(event);
    }
}
