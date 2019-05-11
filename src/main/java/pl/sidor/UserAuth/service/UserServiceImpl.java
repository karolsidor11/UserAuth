package pl.sidor.UserAuth.service;

import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.UserAuth.exception.IncorrectEmailException;
import pl.sidor.UserAuth.exception.IncorrectIDException;
import pl.sidor.UserAuth.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(Integer id) throws IncorrectIDException {
        return Optional.of(userRepository.findById(id).orElseThrow(IncorrectIDException::new));
    }

    @Override
    public Optional<User> findByEmail(String email) throws IncorrectEmailException {
        return Optional.of(userRepository.findByEmail(email)).orElseThrow(IncorrectEmailException::new);
    }

    @Override
    public Optional<List<User>> findALL() {

        List allUsers = (List) userRepository.findAll();

        return !allUsers.isEmpty() ? Optional.of(allUsers) : Optional.of(Collections.emptyList());
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
