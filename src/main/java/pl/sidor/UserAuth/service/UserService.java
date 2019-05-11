package pl.sidor.UserAuth.service;

import models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    Optional<List<User>> findALL();

    User save(User user);

}
