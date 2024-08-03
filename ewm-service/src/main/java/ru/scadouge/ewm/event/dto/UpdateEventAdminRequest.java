package ru.scadouge.ewm.event.dto;

import lombok.*;
import ru.scadouge.ewm.event.dto.enums.EventAdminActionState;
import ru.scadouge.ewm.event.validation.ValidFutureDate;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class UpdateEventAdminRequest {
    @Size(min = 3, max = 120)
    private String title;

    @Size(min = 20, max = 7000)
    private String description;

    @Size(min = 20, max = 2000)
    private String annotation;

    @Positive
    private Long category;

    @ValidFutureDate(minimumMinutes = 60)
    private String eventDate;

    private LocationDto location;

    private Boolean requestModeration;

    private Boolean paid;

    private Integer participantLimit;

    private EventAdminActionState stateAction;
}
