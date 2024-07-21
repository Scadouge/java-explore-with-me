package ru.scadouge.stats.args;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class GetStatsArgs {
    LocalDateTime start;

    LocalDateTime end;

    List<String> uris;

    Boolean unique;
}
