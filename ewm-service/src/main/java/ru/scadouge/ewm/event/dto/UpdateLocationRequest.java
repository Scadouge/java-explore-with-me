package ru.scadouge.ewm.event.dto;

import lombok.*;

import jakarta.validation.constraints.Size;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class UpdateLocationRequest {
    @Size(min = 1, max = 120)
    private String name;

    private Timestamp expirationDate;

    private Boolean permanent;

    private Double lat;

    private Double lon;

    private Double radius;
}
