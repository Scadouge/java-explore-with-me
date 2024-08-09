package ru.scadouge.ewm.event.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class LocationFullDto {
    private Long id;

    private String name;

    private Timestamp expirationDate;

    private Boolean permanent;

    private Double lat;

    private Double lon;

    private Double radius;
}
