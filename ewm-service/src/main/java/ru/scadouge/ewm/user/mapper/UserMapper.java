package ru.scadouge.ewm.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.scadouge.ewm.user.model.User;
import ru.scadouge.ewm.user.dto.NewUserRequest;
import ru.scadouge.ewm.user.dto.UserDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toModel(NewUserRequest newUserRequest);

    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> users);
}
