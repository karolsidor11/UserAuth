package pl.sidor.UserAuth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sidor.UserAuth.repository.UserRepository;
import pl.sidor.UserAuth.validation.UserValidation;

@Configuration
public class ComponentConfiguration {

    private UserRepository userRepository;

    @Autowired
    public ComponentConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserValidation userValidation() {
        return new UserValidation(userRepository);
    }

}
