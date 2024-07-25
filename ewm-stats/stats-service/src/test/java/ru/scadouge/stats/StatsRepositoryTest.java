package ru.scadouge.stats;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.scadouge.stats.model.StatHit;
import ru.scadouge.stats.repository.StatsRepository;
import ru.scadouge.stats.view.StatCountView;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class StatsRepositoryTest {
    private final StatsRepository statsRepository;

    @Test
    void findStatsByTimeFilter() {
        LocalDateTime start = LocalDateTime.of(2024, 7, 21, 10, 0);
        LocalDateTime end = start.plusDays(10);
        StatHit statPast = new StatHit(null, "app1", "uri1", "ip1", start.minusYears(1));
        StatHit statCurrent1 = new StatHit(null, "app1", "uri1", "ip1", start.plusDays(2));
        StatHit statCurrent2 = new StatHit(null, "app1", "uri2", "ip1", start.plusDays(3));
        StatHit statCurrent3 = new StatHit(null, "app1", "uri2", "ip1", start.plusDays(3));
        StatHit statCurrent4 = new StatHit(null, "app1", "uri2", "ip2", start.plusDays(3));
        StatHit statFuture = new StatHit(null, "app1", "uri3", "ip2", start.plusYears(1));

        statsRepository.saveAll(List.of(statPast, statCurrent1, statCurrent2, statCurrent3, statCurrent4, statFuture));

        final List<StatCountView> stats = statsRepository.findStatsCountInTimeIntervalByUri(start, end, List.of("uri1", "uri2", "uri3"), false);
        assertEquals(2, stats.size());
        assertEquals(1, stats.stream().filter(stat -> stat.getUri().equals("uri1")).findFirst().orElseThrow().getCount());
        assertEquals(3, stats.stream().filter(stat -> stat.getUri().equals("uri2")).findFirst().orElseThrow().getCount());

        final List<StatCountView> statsUnique = statsRepository.findStatsCountInTimeIntervalByUri(start, end, List.of("uri1", "uri2", "uri3"), true);
        assertEquals(2, statsUnique.size());
        assertEquals(1, statsUnique.stream().filter(stat -> stat.getUri().equals("uri1")).findFirst().orElseThrow().getCount());
        assertEquals(2, statsUnique.stream().filter(stat -> stat.getUri().equals("uri2")).findFirst().orElseThrow().getCount());
    }
}