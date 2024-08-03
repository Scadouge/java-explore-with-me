package ru.scadouge.ewm.event.args;

import lombok.Builder;
import lombok.Getter;
import ru.scadouge.ewm.event.dto.enums.RequestState;

@Getter
@Builder
public class NewRequestArgs {
    private final Long userId;

    private final Long eventId;

    @Builder.Default
    private final RequestState status = RequestState.PENDING;
}
