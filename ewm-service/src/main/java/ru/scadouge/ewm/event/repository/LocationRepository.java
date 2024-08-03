package ru.scadouge.ewm.event.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.scadouge.ewm.location.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
}
