package pl.sidor.UserAuth.mapper

import models.Role
import org.modelmapper.ModelMapper
import pl.sidor.UserAuth.dto.RoleDto
import spock.lang.Specification

class RoleMapperTest extends Specification {

    private RoleMapper roleMapper
    private ModelMapper modelMapper

    void setup() {
        modelMapper = Mock(ModelMapper)
        roleMapper = new RoleMapper(modelMapper)
    }

    def "should convert Role on RoleDto"() {
        given:
        Role role = Role.builder()
                .id(1)
                .role("USER")
                .build()

        modelMapper.map(role, RoleDto.class) >> RoleDto.builder()
                .id(role.id)
                .role(role.role)
                .build()

        when:
        RoleDto roleDto = roleMapper.mapTo(role)

        then:
        roleDto != null
        roleDto.id == role.id
        roleDto.role == role.role

    }
}
