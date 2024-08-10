package ru.scadouge.ewm.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class NewLocationDto {
    @NotBlank
    @Size(min = 1, max = 120)
    private String name;

    private Timestamp expirationDate;

    private Boolean permanent;

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;

    private Double radius;
}
