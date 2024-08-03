package ru.scadouge.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.scadouge.ewm.error.NotFoundException;
import ru.scadouge.ewm.user.model.User;
import ru.scadouge.ewm.user.repository.UserRepository;
import ru.scadouge.ewm.utils.Pagination;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers(List<Long> ids, int from, int size) {
        if (ids != null) {
            if (ids.size() > 0) {
                return userRepository.findAllByIdIn(ids);
            } else {
                return List.of();
            }
        } else {
            Pageable page = Pagination.getPage(from, size);
            return userRepository.findAllBy(page);
        }
    }

    @Override
    public void deleteUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
        userRepository.delete(user);
    }
}
