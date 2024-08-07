package ru.scadouge.ewm.event.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.scadouge.ewm.event.dto.enums.RequestState;
import ru.scadouge.ewm.event.model.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
    List<Request> findAllByRequesterId(long requesterId);

    Optional<Request> findByRequesterIdAndEventId(long requesterId, long eventId);

    List<Request> findAllByIdInAndStatus(List<Long> requestIds, RequestState status);

    List<Request> findAllByEventId(long eventId);
}
