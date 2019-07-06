package pl.sidor.UserAuth.mapper

import models.Role
import models.User
import org.modelmapper.ModelMapper
import pl.sidor.UserAuth.dto.UserDto
import spock.lang.Specification

class UserMapperTest extends Specification {

    private UserMapper userMapper
    private ModelMapper modelMapper

    void setup() {
        modelMapper = Mock(ModelMapper)
        userMapper = new UserMapper(modelMapper)
    }

    def "should  map User"() {
        given:
        User user = User.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .role(new Role(1, "ADMIN"))
                .build()


        modelMapper.map(user, UserDto.class) >> UserDto.builder()
                .id(user.id)
                .name(user.name)
                .lastName(user.lastName)
                .email(user.email).build()
        when:
        UserDto userDto = userMapper.mapTo(user)

        then:
        userDto != null
        userDto.id == user.id
        userDto.name == user.name
        userDto.lastName == user.lastName
        userDto.email == user.email

    }
}
