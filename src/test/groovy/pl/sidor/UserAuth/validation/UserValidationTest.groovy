package pl.sidor.UserAuth.validation

import pl.sidor.UserAuth.exception.IncorrectEmailException
import pl.sidor.UserAuth.exception.IncorrectIDException
import pl.sidor.UserAuth.repository.UserRepository
import spock.lang.Specification

class UserValidationTest extends Specification {

    private UserRepository userRepository;
    private UserValidation userValidation;

    void setup() {
        userRepository = Mock(UserRepository)
        userValidation = new UserValidation(userRepository)
    }

    def " should return user by id"() {
        given:
        Integer id = 1
        userRepository.existsById(id) >> true

        when:
        boolean result = userValidation.validateID(id)

        then:
        result == true
    }

    def "should return IncorrectIDException"() {
        given:
        Integer id = 9999

        userRepository.existsById(id) >> false

        when:
        userValidation.validateID(id)

        then:
        thrown(IncorrectIDException)
    }

    def "email is correct"() {
        given:
        String email = "karolsidor11@wp.pl"

        when:
        boolean result = userValidation.validateEmail(email)

        then:
        result == true
    }

    def "should return IncorrectEmailException"() {
        given:
        String email = "karol123.pl"

        when:
        boolean result = userValidation.validateEmail(email)

        then:
        result == false
        thrown(IncorrectEmailException)
    }
}
