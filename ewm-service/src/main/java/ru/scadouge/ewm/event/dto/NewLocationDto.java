package ru.scadouge.ewm.event.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
