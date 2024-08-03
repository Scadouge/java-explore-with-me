package ru.scadouge.ewm.event.args;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NewCompilationArgs {
    private final String title;

    @Builder.Default
    private final List<Long> events = List.of();

    @Builder.Default
    private final Boolean pinned = false;
}
