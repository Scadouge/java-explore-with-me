package ru.scadouge.ewm.event.dto;

import lombok.*;
import ru.scadouge.ewm.event.dto.enums.RequestState;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class ParticipationRequestDto {
    private Long id;

    private Long event;

    private String created;

    private Long requester;

    private RequestState status;
}
