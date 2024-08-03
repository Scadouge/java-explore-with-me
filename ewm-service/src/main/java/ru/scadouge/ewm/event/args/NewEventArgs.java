package ru.scadouge.ewm.event.args;

import lombok.Builder;
import lombok.Getter;
import ru.scadouge.ewm.event.dto.enums.EventState;
import ru.scadouge.ewm.location.Location;

import java.sql.Timestamp;

@Getter
@Builder
public class NewEventArgs {
    private final String title;

    private final String description;

    private final String annotation;

    private final Long category;

    private final Timestamp eventDate;

    private final Location location;

    private final Boolean requestModeration;

    private final Boolean paid;

    private final Integer participantLimit;

    @Builder.Default
    private final Long confirmedRequests = 0L;

    @Builder.Default
    private final EventState state = EventState.PENDING;
}