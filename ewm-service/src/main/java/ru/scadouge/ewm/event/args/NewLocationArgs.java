package ru.scadouge.ewm.event.args;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class NewLocationArgs {
    private final Long id;

    private final String name;

    private final Timestamp expirationDate;

    @Builder.Default
    private final Boolean permanent = false;

    private final Double lat;

    private final Double lon;

    @Builder.Default
    private final Double radius = 0.0D;
}
