package ru.scadouge.ewm.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.scadouge.ewm.event.dto.enums.EventState;
import ru.scadouge.ewm.event.model.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findAllByInitiatorId(long userId, Pageable page);

    List<Event> findAll(Specification<Event> spec, Pageable page);

    Optional<Event> findEventByIdAndState(long eventId, EventState status);

    List<Event> findAllByIdIn(List<Long> ids);
}
