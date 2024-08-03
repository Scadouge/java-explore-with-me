package ru.scadouge.ewm.event.dto;

import lombok.*;
import ru.scadouge.ewm.event.dto.enums.RequestState;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class EventRequestStatusUpdateRequest {
    @Builder.Default
    private List<Long> requestIds  = List.of();

    private RequestState status;
}
