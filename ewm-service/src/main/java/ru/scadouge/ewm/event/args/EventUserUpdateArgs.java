package ru.scadouge.ewm.event.args;

import lombok.Builder;
import lombok.Getter;
import ru.scadouge.ewm.event.dto.enums.EventUserActionState;

import java.sql.Timestamp;

@Getter
@Builder
public class EventUserUpdateArgs {
    private final String title;

    private final String description;

    private final String annotation;

    private final Long category;

    private final Timestamp eventDate;

    private final NewLocationArgs location;

    private final Boolean requestModeration;

    private final Boolean paid;

    private final Integer participantLimit;

    private final EventUserActionState stateAction;
}
