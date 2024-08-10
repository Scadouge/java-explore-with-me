package ru.scadouge.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.scadouge.ewm.event.args.NewLocationArgs;
import ru.scadouge.ewm.event.args.UpdateLocationArgs;
import ru.scadouge.ewm.event.dto.LocationFullDto;
import ru.scadouge.ewm.event.dto.NewLocationDto;
import ru.scadouge.ewm.event.dto.UpdateLocationRequest;
import ru.scadouge.ewm.event.mapper.LocationMapper;
import ru.scadouge.ewm.event.model.Location;
import ru.scadouge.ewm.event.service.LocationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/admin/locations")
@Slf4j
@RequiredArgsConstructor
public class LocationAdminController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationFullDto createLocationByAdmin(@RequestBody @Valid NewLocationDto newLocationDto) {
        log.info("ADMIN: Создание новой локации newLocationDto={}", newLocationDto);
        NewLocationArgs args = locationMapper.toNewLocationArgs(newLocationDto);
        Location location = locationService.createLocation(args);
        return locationMapper.toLocationFullDto(location);
    }

    @GetMapping
    public List<LocationFullDto> getAllLocationsByAdmin(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                        @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("ADMIN: Получение списка локаций from={}, size={}", from, size);
        List<Location> locations = locationService.getAllLocationsByAdmin(from, size);
        return locationMapper.toLocationFullDto(locations);
    }

    @PatchMapping("/{locationId}")
    public LocationFullDto updateLocationByAdmin(@PathVariable long locationId,
                                                 @RequestBody @Valid UpdateLocationRequest updateLocationRequest) {
        UpdateLocationArgs updateLocationArgs = locationMapper.toUpdateLocationArgs(updateLocationRequest);
        log.info("ADMIN: Обновление локации locationId={}, updateLocationArgs={}", locationId, updateLocationArgs);
        Location location = locationService.updateLocation(locationId, updateLocationArgs);
        return locationMapper.toLocationFullDto(location);
    }
}
