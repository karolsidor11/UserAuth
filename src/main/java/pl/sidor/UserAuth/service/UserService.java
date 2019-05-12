package pl.sidor.UserAuth.service;

import models.User;
import pl.sidor.UserAuth.exception.IncorrectEmailException;
import pl.sidor.UserAuth.exception.IncorrectIDException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Integer id) throws Exception;

    User findByEmail(String email) throws IncorrectEmailException;

    List<User> findALL();

    User save(User user);

    boolean deleteUser(Integer id) throws IncorrectIDException;

}
