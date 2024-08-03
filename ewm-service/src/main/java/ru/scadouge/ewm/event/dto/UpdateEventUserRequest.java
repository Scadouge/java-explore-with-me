package ru.scadouge.ewm.event.dto;

import lombok.*;
import ru.scadouge.ewm.event.dto.enums.EventUserActionState;
import ru.scadouge.ewm.event.validation.ValidFutureDate;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class UpdateEventUserRequest {
    @Size(min = 3, max = 120)
    private String title;

    @Size(min = 20, max = 7000)
    private String description;

    @Size(min = 20, max = 2000)
    private String annotation;

    @Positive
    private Long category;

    @ValidFutureDate(minimumMinutes = 120)
    private String eventDate;

    private LocationDto location;

    private Boolean requestModeration;

    private Boolean paid;

    @Positive
    private Integer participantLimit;

    private EventUserActionState stateAction;
}
