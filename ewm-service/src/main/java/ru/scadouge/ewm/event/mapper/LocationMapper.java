package ru.scadouge.ewm.event.mapper;

import org.mapstruct.*;
import ru.scadouge.ewm.event.args.NewLocationArgs;
import ru.scadouge.ewm.event.args.UpdateLocationArgs;
import ru.scadouge.ewm.event.dto.LocationDto;
import ru.scadouge.ewm.event.dto.LocationFullDto;
import ru.scadouge.ewm.event.dto.NewLocationDto;
import ru.scadouge.ewm.event.dto.UpdateLocationRequest;
import ru.scadouge.ewm.event.model.Location;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    @Mapping(target = "id", ignore = true)
    Location toLocation(NewLocationArgs newLocationArgs);

    LocationDto toLocationDto(Location location);

    LocationFullDto toLocationFullDto(Location location);

    List<LocationFullDto> toLocationFullDto(List<Location> locations);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLocation(@MappingTarget Location location, UpdateLocationArgs updateArgs);

    // ============= ARGS ============= //

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    UpdateLocationArgs toUpdateLocationArgs(UpdateLocationRequest updateLocationRequest);

    @Mapping(target = "radius", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "expirationDate", ignore = true)
    @Mapping(target = "permanent", ignore = true)
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    NewLocationArgs toNewLocationArgs(LocationDto locationDto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    NewLocationArgs toNewLocationArgs(NewLocationDto newLocationDto);
}
