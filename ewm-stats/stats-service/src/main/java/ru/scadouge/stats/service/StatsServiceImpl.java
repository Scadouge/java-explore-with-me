package ru.scadouge.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.scadouge.stats.args.GetStatsArgs;
import ru.scadouge.stats.model.StatHit;
import ru.scadouge.stats.repository.StatsRepository;
import ru.scadouge.stats.view.StatCountView;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public void hit(StatHit statHit) {
        statsRepository.save(statHit);
    }

    @Override
    public List<StatCountView> getStats(GetStatsArgs args) {
        return statsRepository.findStatsCountInTimeIntervalByUri(args.getStart(), args.getEnd(), args.getUris(), args.getUnique());
    }
}
