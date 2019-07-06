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
import pl.sidor.UserAuth.validation.UserValidation;

import java.util.List;
import java.util.Optional;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserValidation userValidation;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserValidation userValidation) {
        this.userRepository = userRepository;
        this.userValidation=userValidation;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) throws IncorrectEmailException {
        userValidation.validateEmail(email);
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public Optional<User> login(String email, String password) {

        return Optional.ofNullable(userRepository.findByEmailAndPassword(email, password));
    }

    @Override
    public List<User> findALL() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.of(userRepository.save(user));
    }

    @Override
    public boolean deleteUser(Integer id) {
        try {
            userValidation.validateID(id);
            userRepository.deleteById(id);
            return true;
        } catch (IncorrectIDException e) {
            e.printStackTrace();
            return false;
        }
    }
}
