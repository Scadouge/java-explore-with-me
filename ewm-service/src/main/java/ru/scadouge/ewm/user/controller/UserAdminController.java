package ru.scadouge.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.scadouge.ewm.user.model.User;
import ru.scadouge.ewm.user.dto.NewUserRequest;
import ru.scadouge.ewm.user.dto.UserDto;
import ru.scadouge.ewm.user.mapper.UserMapper;
import ru.scadouge.ewm.user.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@Slf4j
@Validated
@RequiredArgsConstructor
public class UserAdminController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info("ADMIN: Создание нового пользователя newUserRequest={}", newUserRequest);
        User user = userService.createUser(userMapper.toModel(newUserRequest));
        return userMapper.toUserDto(user);
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                  @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("ADMIN: Получение списка пользователей ids={}, from={}, size={}", ids, from, size);
        List<User> users = userService.getUsers(ids, from, size);
        return userMapper.toUserDto(users);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long userId) {
        log.info("ADMIN: Удаление пользователя userId={}", userId);
        userService.deleteUser(userId);
    }
}
