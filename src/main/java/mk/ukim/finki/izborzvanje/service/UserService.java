package mk.ukim.finki.izborzvanje.service;

import mk.ukim.finki.izborzvanje.model.User;

import java.sql.Struct;
import java.util.List;

public interface UserService {

    List<User> listAllNoCriteria();
    User findById(String id);

    User create(User user);
}
