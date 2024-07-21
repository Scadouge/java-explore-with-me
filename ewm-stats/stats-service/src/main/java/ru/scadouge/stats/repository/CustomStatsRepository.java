package ru.scadouge.stats.repository;

import ru.scadouge.stats.view.StatCountView;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomStatsRepository {
    List<StatCountView> findStatsCountInTimeIntervalByUri(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
