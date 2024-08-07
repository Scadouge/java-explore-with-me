package ru.scadouge.stats.repository;

import org.springframework.stereotype.Component;
import ru.scadouge.stats.view.StatCountView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CustomStatsRepositoryImpl implements CustomStatsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StatCountView> findStatsCountInTimeIntervalByUri(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        String countClause = "st.ip";
        if (unique) {
            countClause = "DISTINCT st.ip";
        }
        String urisInClause = "";
        if (uris.size() > 0) {
            urisInClause = "st.uri IN :uris AND ";
        }

        String query = String.format("SELECT new ru.scadouge.stats.view.StatCountView(st.app, st.uri, COUNT(%s) AS hits) FROM StatHit st WHERE " +
                "%s" +
                "st.timestamp > :start AND st.timestamp < :end " +
                "GROUP BY st.app, st.uri " +
                "ORDER BY hits DESC", countClause, urisInClause);
        TypedQuery<StatCountView> typedQuery = entityManager.createQuery(query, StatCountView.class)
                .setParameter("start", start)
                .setParameter("end", end);

        if (uris.size() > 0) {
            typedQuery.setParameter("uris", uris);
        }

        return typedQuery.getResultList();
    }

    @Override
    public List<StatCountView> findStatsCountByUri(List<String> uris, boolean unique) {
        String countClause = "st.ip";
        if (unique) {
            countClause = "DISTINCT st.ip";
        }
        String urisInClause = "";
        if (uris.size() > 0) {
            urisInClause = "st.uri IN :uris ";
        }

        String query = String.format("SELECT new ru.scadouge.stats.view.StatCountView(st.app, st.uri, COUNT(%s) AS hits) FROM StatHit st WHERE " +
                "%s" +
                "GROUP BY st.app, st.uri " +
                "ORDER BY hits DESC", countClause, urisInClause);
        TypedQuery<StatCountView> typedQuery = entityManager.createQuery(query, StatCountView.class);

        if (uris.size() > 0) {
            typedQuery.setParameter("uris", uris);
        }

        return typedQuery.getResultList();
    }
}
