package ru.scadouge.ewm.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.scadouge.ewm.utils.TimeHelper;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DateConversionMapper {
    default Timestamp toTimestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Timestamp.valueOf(localDateTime);
    }

    default String fromTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime().format(TimeHelper.DATE_TIME_FORMATTER);
    }
}
