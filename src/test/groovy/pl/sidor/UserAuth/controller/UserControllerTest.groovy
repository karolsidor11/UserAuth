package pl.sidor.UserAuth.controller

import models.Role
import models.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pl.sidor.UserAuth.service.UserService
import pl.sidor.UserAuth.service.UserServiceImpl
import spock.lang.Specification

class UserControllerTest extends Specification {

    private UserController userController
    private UserService userService

    void setup() {
        userService = Mock(UserServiceImpl)
        userController = new UserController(userService)
    }

    def "should get User by ID"() {
        given:
        Integer id = 1
        User user = User.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .role(new Role(1, "ADMIN"))
                .build()

        userService.findById(1) >> Optional.of(user)

        when:
        ResponseEntity<User> responseEntity = userController.findUserById(id)

        then:
        responseEntity.statusCodeValue == 200
        responseEntity.body == user
        responseEntity.statusCode == HttpStatus.OK

    }

    def "should  return  all Users"() {
        given:
        List<User> userList = new ArrayList<>()
        Role role = Role.builder()
                .id(1)
                .role("USER")
                .build()
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
                .lastName("Kowalski")
                .email("kowalski@wp.pl")
                .role(role)
                .build()

        userList.add(user1)
        userList.add(user2)

        userService.findALL() >> userList

        when:
        ResponseEntity<List<User>> responseEntity = userController.findAllUsers()

        then:
        responseEntity.body == userList
        responseEntity.statusCodeValue == 200
        responseEntity.statusCode == HttpStatus.OK
    }

    def "should find User by Email"() {
        given:
        String email = "karolsidor11@wp.pl"

        User user = User.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .role(new Role(1, "USER"))
                .build()

        userService.findByEmail(email) >> user

        when:
        ResponseEntity<User> responseEntity = userController.findUserByEmail(email)

        then:
        responseEntity.statusCodeValue == 200
        responseEntity.body == user
        responseEntity.statusCode == HttpStatus.OK
    }

    def "should save User in database"() {
        given:
        User user = prepareUser()
        userService.save(user) >> user

        when:
        ResponseEntity responseEntity = userController.saveUser(user)

        then:
        responseEntity.statusCodeValue == 200
        responseEntity.body == user
        responseEntity.statusCode == HttpStatus.OK
    }

    def "should delete User by ID"() {
        given:
        Integer id = 1
        userService.deleteUser(id) >> true

        when:
        ResponseEntity responseEntity = userController.deleteUserByID(id)

        then:
        responseEntity.statusCodeValue == 200
        responseEntity.statusCode == HttpStatus.OK
        responseEntity.body.equals(true)

    }

    def "should throw IncorrectIDException when delete User by ID"() {
        given:
        Integer id = 999
        userService.deleteUser(id) >> false

        when:
        ResponseEntity responseEntity = userController.deleteUserByID(id)

        then:
        responseEntity.body.equals(false)
        responseEntity.statusCode == HttpStatus.BAD_REQUEST
        responseEntity.statusCodeValue==400

    }

    private static User prepareUser() {
        Role role = Role.builder()
                .id(1)
                .role("USER")
                .build()
        User user1 = User.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .role(role)
                .build()
        user1
    }
}
