package pl.sidor.UserAuth.service

import models.Role
import models.User
import pl.sidor.UserAuth.exception.IncorrectEmailException
import pl.sidor.UserAuth.exception.IncorrectIDException
import pl.sidor.UserAuth.repository.UserRepository
import spock.lang.Specification

class UserServiceImplTest extends Specification {

    private UserRepository userRepository
    private UserService userService

    void setup() {
        userRepository = Mock(UserRepository)
        userService = new UserServiceImpl(userRepository)
    }

    def "should return User by ID"() {
        given:
        Integer id = 2
        User user = prepareUser()

        userRepository.existsById(id) >> true
        userRepository.findById(id) >> Optional.of(user)

        when:
        Optional<User> actualUser = userService.findById(id)
        then:
        actualUser.isPresent()
        actualUser.get() == user
    }


    def "should throw IncorrectIDException"() {
        given:
        Integer id = -2
        userRepository.findById(id) >> Optional.empty()

        when:
        userService.findById(id)

        then:
        thrown(IncorrectIDException)
    }

    def "should return User by email"() {
        given:
        String email = "karolsidor11@wp.pl"
        User user = prepareUser()
        userRepository.findByEmail(email) >> user

        when:
        User actualUser = userService.findByEmail(email)

        then:
        actualUser == user
    }

    def "should throw IncorrectEmailException"() {
        given:
        String email = "aaaa"
        userRepository.findByEmail(email) >> null

        when:
        userService.findByEmail(email)

        then:
        thrown(IncorrectEmailException)

    }

    def "should return all Users"() {
        given:
        List<User> userList = new ArrayList<>()

        userList.add(prepareUser())
        userRepository.findAll() >> userList

        when:
        List<User> actualUsers = userService.findALL()

        then:
        !actualUsers.isEmpty()
        actualUsers.size() == 1
        actualUsers == userList
    }

    def "should return empty collections"() {
        given:
        userRepository.findAll() >> Collections.emptyList()

        when:
        List<User> actualUserList = userService.findALL()

        then:
        actualUserList.isEmpty()
    }

    def "should save  user"() {
        given:
        User user = prepareUser()
        userRepository.save(user) >> user

        when:
        User actualUser = userService.save(user)

        then:
        1 * userRepository.save(user)
        
    }

    def "should  delete User by ID"() {
        given:
        Integer id = 1
        userRepository.existsById(id) >> true
        userRepository.deleteUserById(id)

        when:
        userService.deleteUser(id)

        then:
        1 * userRepository.deleteUserById(id)
    }

    def "should return IncorrectIDException"() {
        given:
        Integer id = 9999
        userRepository.existsById(id) >> false
        userRepository.deleteUserById(id)

        when:
        userService.deleteUser(id)

        then:
        thrown(IncorrectIDException)
    }

    private static User prepareUser() {
        Role role = Role.builder()
                .id(1)
                .role("USER")
                .build()
        User user = User.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .role(role)
                .build()
        user
    }
}
