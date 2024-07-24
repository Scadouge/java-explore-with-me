package ru.scadouge.stats;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class EndpointHit {
    private String app;

    private String uri;

    private String ip;

    private String timestamp;
}
