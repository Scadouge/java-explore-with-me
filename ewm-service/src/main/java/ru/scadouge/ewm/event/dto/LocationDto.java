package ru.scadouge.ewm.event.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class LocationDto {
    @NotNull
    @Positive
    private Double lat;

    @NotNull
    @Positive
    private Double lon;
}
