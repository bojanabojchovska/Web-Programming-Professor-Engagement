package mk.ukim.finki.izborzvanje.service.impl;

import mk.ukim.finki.izborzvanje.model.User;
import mk.ukim.finki.izborzvanje.model.exceptions.InvalidUsernameException;
import mk.ukim.finki.izborzvanje.repository.UserRepository;
import mk.ukim.finki.izborzvanje.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listAllNoCriteria() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(InvalidUsernameException::new);
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }
}
