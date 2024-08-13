package ru.scadouge.ewm.event.args;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
@ToString
public class AdminSearchEventsArgs {
    private final List<Long> users;

    private final List<String> states;

    private final List<Long> categories;

    private final Timestamp rangeStart;

    private final Timestamp rangeEnd;

    private final Double lat;

    private final Double lon;

    private final Double radius;

    private final int from;

    private final int size;
}
