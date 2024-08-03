package ru.scadouge.ewm.event.args;

import lombok.Builder;
import lombok.Getter;
import ru.scadouge.ewm.event.model.Event;

@Getter
@Builder
public class EventWithViewsArgs {
    private final Event event;
    private final long views;
}
