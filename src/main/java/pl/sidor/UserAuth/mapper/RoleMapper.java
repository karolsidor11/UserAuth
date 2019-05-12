package pl.sidor.UserAuth.mapper;

import models.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.sidor.UserAuth.dto.RoleDto;

@Component
public class RoleMapper {

    private ModelMapper modelMapper;

    @Autowired
    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RoleDto mapTo(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }
}
