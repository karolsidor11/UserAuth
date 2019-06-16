package pl.sidor.UserAuth.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sidor.UserAuth.exception.IncorrectEmailException;
import pl.sidor.UserAuth.exception.IncorrectIDException;
import pl.sidor.UserAuth.repository.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class UserValidation {

    private UserRepository userRepository;

    @Autowired
    public UserValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validateID(Integer id) throws IncorrectIDException {
        if (!userRepository.existsById(id)) {
            throw new IncorrectIDException("Użytkownik o podanym ID nie istnieje w bazie !!!");
        } else {
            return true;
        }
    }

    public boolean validateEmail(String email) throws IncorrectEmailException {

        String wyrazenie = ".+@.+\\.[a-z]+";
        Pattern pattern = Pattern.compile(wyrazenie);

        if (!email.isEmpty() && email.length() < 255) {

            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                throw new IncorrectEmailException("Nieprawidłowy adress email !!!");
            }
        }
        return true;
    }
}
