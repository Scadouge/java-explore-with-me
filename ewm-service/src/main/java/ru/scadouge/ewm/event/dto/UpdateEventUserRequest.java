package ru.scadouge.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.scadouge.ewm.event.dto.enums.EventUserActionState;
import ru.scadouge.ewm.event.validation.ValidFutureDate;
import ru.scadouge.ewm.utils.TimeHelper;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

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
    @JsonFormat(pattern = TimeHelper.DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean requestModeration;

    private Boolean paid;

    @Positive
    private Integer participantLimit;

    private EventUserActionState stateAction;
}
