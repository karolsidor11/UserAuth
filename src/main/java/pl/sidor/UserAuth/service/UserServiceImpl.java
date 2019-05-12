package pl.sidor.UserAuth.service;

import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.UserAuth.exception.IncorrectEmailException;
import pl.sidor.UserAuth.exception.IncorrectIDException;
import pl.sidor.UserAuth.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(Integer id) throws IncorrectIDException {
        validateID(id);
        return Optional.of(userRepository.findById(id).orElseThrow(IncorrectIDException::new));
    }

    @Override
    public User findByEmail(String email) throws IncorrectEmailException {

        return Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(IncorrectEmailException::new);
    }

    @Override
    public List<User> findALL() {

        return (List<User>) userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(Integer id) throws IncorrectIDException {
        validateID(id);
        userRepository.deleteUserById(id);
        return true;
    }

    private void validateID(Integer id) throws IncorrectIDException {
        if (userRepository.existsById(id)) {
        } else {
            throw new IncorrectIDException("Nieprawidłowy identyfikator !!!");
        }
    }
}
