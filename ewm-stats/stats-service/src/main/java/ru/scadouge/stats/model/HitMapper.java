package ru.scadouge.stats.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.scadouge.stats.dto.EndpointHit;
import ru.scadouge.stats.dto.TimeUtils;
import ru.scadouge.stats.dto.ViewStats;
import ru.scadouge.stats.view.StatCountView;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HitMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", source = "timestamp", dateFormat = TimeUtils.DATE_TIME_PATTERN)
    StatHit toModel(EndpointHit endpointHit);

    List<ViewStats> toViewStatsDto(List<StatCountView> statCountViews);

    @Mapping(target = "hits", source = "count")
    ViewStats toViewStats(StatCountView statCountView);
}
