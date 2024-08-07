package ru.scadouge.ewm.event.args;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@ToString
public class SearchCompilationsArgs {
    private final Boolean pinned;

    private final int from;

    private final int size;
}
