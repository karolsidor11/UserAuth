package pl.sidor.UserAuth.controller;

import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.sidor.UserAuth.service.UserService;
import pl.sidor.UserAuth.token.UserToken;

import java.util.Optional;

@RestController
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "login/{name}/{password}")
    public String loginUser(@PathVariable String name, @PathVariable String password) {


        Optional<User> byEmailAndPassword = Optional.of(userService.login(name, password).get());

        return UserToken.createTokenForUser(byEmailAndPassword.get());
    }
}
