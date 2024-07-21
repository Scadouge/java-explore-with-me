package ru.scadouge.stats.view;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class StatCountView {
    private final String app;
    private final String uri;
    private final Long count;
}
