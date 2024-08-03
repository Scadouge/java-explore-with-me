package ru.scadouge.ewm.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.scadouge.ewm.event.dto.ParticipationRequestDto;
import ru.scadouge.ewm.event.model.Event;
import ru.scadouge.ewm.event.model.Request;
import ru.scadouge.ewm.user.model.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "created", ignore = true)
    Request toModel(User requester, Event event);

    @Mapping(target = "event", source = "request.event.id")
    @Mapping(target = "requester", source = "request.requester.id")
    ParticipationRequestDto toParticipationRequestDto(Request request);

    List<ParticipationRequestDto> toParticipationRequestDto(List<Request> request);
}
