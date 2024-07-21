package ru.scadouge.stats.repository;

import org.springframework.data.repository.CrudRepository;
import ru.scadouge.stats.model.StatHit;

public interface StatsRepository extends CrudRepository<StatHit, Long>, CustomStatsRepository {
}
