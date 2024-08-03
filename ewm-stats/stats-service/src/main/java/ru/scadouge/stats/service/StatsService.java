package ru.scadouge.stats.service;

import ru.scadouge.stats.args.GetStatsArgs;
import ru.scadouge.stats.model.StatHit;
import ru.scadouge.stats.view.StatCountView;

import java.util.List;

public interface StatsService {
    void hit(StatHit statHit);

    List<StatCountView> getStats(GetStatsArgs args);

    List<StatCountView> getViewsStats(List<String> uris, boolean unique);
}
