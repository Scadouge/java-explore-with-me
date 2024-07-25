package ru.scadouge.stats.args;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import ru.scadouge.stats.model.StatHit;

import java.util.Map;

@Value
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class GetStatsViewArgs {
    Map<Long, StatHit> viewStats;

    Map<Long, Integer> hitCount;
}
