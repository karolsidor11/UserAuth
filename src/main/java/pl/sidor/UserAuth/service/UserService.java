package pl.sidor.UserAuth.service;

import models.User;
import pl.sidor.UserAuth.exception.IncorrectEmailException;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(Integer id) throws Exception;

    Optional<User> findByEmail(String email) throws IncorrectEmailException;

    Optional findALL();

    User save(User user);

}
