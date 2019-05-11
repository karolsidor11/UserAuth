package pl.sidor.UserAuth.service

import models.Role
import models.User
import pl.sidor.UserAuth.exception.IncorrectEmailException
import pl.sidor.UserAuth.exception.IncorrectIDException
import pl.sidor.UserAuth.repository.UserRepository
import spock.lang.Ignore
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

        userRepository.findById(id) >> Optional.of(user)

        when:
        User actualUser = userService.findById(id).get()
        then:
        actualUser == user
    }

    def "should return  IncorrectIDException"() {
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
        Role role = Role.builder()
                .id(1)
                .role("ADMIN")
                .build()

        User user = User.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email(email)
                .role(role)
                .build()

        userRepository.findByEmail(email) >> Optional.of(user)
        when:
        User actualUser = userService.findByEmail(email).get()

        then:
        actualUser == user
    }

    @Ignore
    def "should return IncorrectEmailException"() {
        given:
        String email = "aaaa"
        userRepository.findByEmail(email) >> Optional.empty()

        when:
        userService.findByEmail(email)

        then:
        thrown(IncorrectEmailException)

    }

    def "should return all Users"() {
        given:
        List<User> userList = new ArrayList<>()
        Role role = Role.builder().id(1).role("USER").build()
        User user1 = User.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .role(role)
                .build()
        User user2 = User.builder()
                .id(2)
                .name("Jan")
                .lastName("Nowak")
                .email("jan@wp.pl")
                .role(role)
                .build()

        userList.add(user1)
        userList.add(user2)
        userRepository.findAll() >> userList

        when:
        List<User> actualUsers = userService.findALL().get()

        then:
        !actualUsers.isEmpty()
        actualUsers.size() == 2
        actualUsers == userList
    }

    def "should return empty collections"() {
        given:
        userRepository.findAll() >> Collections.emptyList()

        when:
        List<User> actualUserList = userService.findALL().get()

        then:
        actualUserList.size() == 0
        actualUserList.isEmpty()
    }

    def "should save  user"() {
        given:
        Role role = Role.builder()
                .id(1)
                .role("ADMIN")
                .build()

        User user = User.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .role(role)
                .build()

        userRepository.save(user) >> user

        when:
        userService.save(user)

        then:
        1 * userRepository.save(user)
    }
}
