package ru.scadouge.ewm.event.service;

import ru.scadouge.ewm.event.args.NewLocationArgs;
import ru.scadouge.ewm.event.args.UpdateLocationArgs;
import ru.scadouge.ewm.event.model.Location;

import java.util.List;

public interface LocationService {
    Location createLocation(NewLocationArgs newLocationArgs);

    List<Location> getAllLocationsByAdmin(int from, int size);

    Location updateLocation(long locationId, UpdateLocationArgs args);
}
