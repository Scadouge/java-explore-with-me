package ru.scadouge.stats;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class EndpointHit {
    String app;

    String uri;

    String ip;

    String timestamp;
}
