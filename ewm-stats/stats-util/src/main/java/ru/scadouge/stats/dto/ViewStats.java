package ru.scadouge.stats.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class ViewStats {
    private String app;

    private String uri;

    private int hits;
}
