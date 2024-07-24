package ru.scadouge.stats.args;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.scadouge.stats.Utils;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArgsMapper {
    @Mapping(target = "start", source = "start", dateFormat = Utils.DATE_TIME_PATTERN)
    @Mapping(target = "end", source = "end", dateFormat = Utils.DATE_TIME_PATTERN)
    @Mapping(target = "uris", source = "uris", defaultExpression = "java(List.of())")
    GetStatsArgs toGetStatsArgs(String start, String end, List<String> uris, Boolean unique);
}