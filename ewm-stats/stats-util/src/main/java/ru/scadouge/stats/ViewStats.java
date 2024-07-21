package ru.scadouge.stats;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ViewStats {
    private String app;

    private String uri;

    private int hits;
}
