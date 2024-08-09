package ru.scadouge.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.scadouge.ewm.error.NotFoundException;
import ru.scadouge.ewm.event.args.NewLocationArgs;
import ru.scadouge.ewm.event.args.UpdateLocationArgs;
import ru.scadouge.ewm.event.mapper.LocationMapper;
import ru.scadouge.ewm.event.model.Location;
import ru.scadouge.ewm.event.repository.LocationRepository;
import ru.scadouge.ewm.utils.Pagination;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    @Transactional
    public Location createLocation(NewLocationArgs newLocationArgs) {
        Location location = locationMapper.toLocation(newLocationArgs);
        return locationRepository.save(location);
    }

    @Override
    public List<Location> getAllLocationsByAdmin(int from, int size) {
        Pageable page = Pagination.getPage(from, size);
        return locationRepository.findAll(page);
    }

    @Override
    public Location updateLocation(long locationId, UpdateLocationArgs args) {
        Location location = locationRepository.findById(locationId).orElseThrow(() ->
                new NotFoundException(Location.class, locationId));
        locationMapper.updateLocation(location, args);
        return locationRepository.save(location);
    }
}
