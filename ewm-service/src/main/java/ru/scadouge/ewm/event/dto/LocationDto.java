package ru.scadouge.ewm.event.dto;

import lombok.*;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class LocationDto {
    @Positive
    private Long id;

    private Double lat;

    private Double lon;
}
