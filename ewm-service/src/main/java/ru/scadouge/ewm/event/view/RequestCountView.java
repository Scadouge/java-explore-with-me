package ru.scadouge.ewm.event.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestCountView {
    private final long eventId;

    private final long requestCount;
}
