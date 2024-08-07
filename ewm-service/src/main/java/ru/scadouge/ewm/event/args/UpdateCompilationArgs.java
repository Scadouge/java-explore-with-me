package ru.scadouge.ewm.event.args;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@ToString
public class UpdateCompilationArgs {
    private final String title;

    private final List<Long> events;

    private final Boolean pinned;
}
