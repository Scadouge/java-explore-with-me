package ru.scadouge.ewm.user.service;

import ru.scadouge.ewm.user.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    List<User> getUsers(List<Long> ids, int from, int size);

    void deleteUser(long id);
}
