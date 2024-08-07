package ru.scadouge.ewm.event.service;

import ru.scadouge.ewm.event.args.*;
import ru.scadouge.ewm.event.model.Event;

import java.util.List;

public interface EventService {
    Event createEventByUser(Long userId, NewEventArgs args);

    List<EventWithViewsArgs> getEventsByUser(long userId, int from, int size);

    EventWithViewsArgs getEventByUser(long userId, long eventId);

    EventWithViewsArgs updateEventByUser(long userId, long eventId, EventUserUpdateArgs eventUserUpdateArgs);

    List<EventWithViewsArgs> getAllEventsByAdmin(AdminSearchEventsArgs args);

    EventWithViewsArgs updateEventByAdmin(long eventId, EventAdminUpdateArgs eventAdminUpdateArgs);

    List<EventWithViewsArgs> getAllPublishedEventsByPublic(SearchEventsArgs args);

    EventWithViewsArgs getEventByPublic(long eventId);
}
