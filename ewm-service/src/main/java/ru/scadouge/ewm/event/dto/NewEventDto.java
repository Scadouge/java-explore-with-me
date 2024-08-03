package ru.scadouge.ewm.event.dto;

import lombok.*;
import ru.scadouge.ewm.event.validation.ValidFutureDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class NewEventDto {
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;

    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;

    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    @NotNull
    private Long category;

    @NotBlank
    @ValidFutureDate(minimumMinutes = 120)
    private String eventDate;

    @NotNull
    private LocationDto location;

    @Builder.Default
    private Boolean requestModeration = true;

    @Builder.Default
    private Boolean paid = false;

    @Builder.Default
    @PositiveOrZero
    private Integer participantLimit = 0;
}
