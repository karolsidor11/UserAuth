package pl.sidor.UserAuth.mapper;

import models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.sidor.UserAuth.dto.UserDto;

@Component
public class UserMapper {

    private ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto mapTo(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
