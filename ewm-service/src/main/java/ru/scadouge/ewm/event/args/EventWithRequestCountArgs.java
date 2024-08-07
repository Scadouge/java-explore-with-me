package ru.scadouge.ewm.event.args;

import lombok.Builder;
import lombok.Getter;
import ru.scadouge.ewm.event.model.Event;

@Getter
@Builder
public class EventWithRequestCountArgs {
    private final Event event;

    private final Long count;
}
