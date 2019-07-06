package pl.sidor.UserAuth.validation

import pl.sidor.UserAuth.exception.IncorrectEmailException
import pl.sidor.UserAuth.exception.IncorrectIDException
import pl.sidor.UserAuth.repository.UserRepository
import spock.lang.Specification
import spock.lang.Unroll

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


    @Unroll
    def "parametrized tests"() {
        when:

        userRepository.existsById(1) >> true
        boolean result = userValidation.validateEmail(object)
        boolean result1 = userValidation.validateID(numer)
        then:
        result == expectedResult
        result1 == expectedResult1

        where:

        object               | expectedResult | numer | expectedResult1
        "jan"                | false          | 1     | true
        "karolsidor11@wp.pl" | true           | 2     | false
    }
}
