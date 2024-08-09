package ru.scadouge.ewm.event.args;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class UpdateLocationArgs {
    private final String name;

    private final Timestamp expirationDate;

    private final Boolean permanent;

    private final Double lat;

    private final Double lon;

    private final Double radius;
}
