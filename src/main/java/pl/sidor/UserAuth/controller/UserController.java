package pl.sidor.UserAuth.controller;

import models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.UserAuth.dto.UserDto;
import pl.sidor.UserAuth.exception.IncorrectEmailException;
import pl.sidor.UserAuth.exception.IncorrectIDException;
import pl.sidor.UserAuth.mapper.UserMapper;
import pl.sidor.UserAuth.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "findUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAllUsers() {

        List<User> allUsers = userService.findALL();

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping(value = "user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findUserById(@PathVariable Integer id) {
        try {
            Optional<User> byId = userService.findById(id);
            HttpStatus httpStatus = byId.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(byId.get(), httpStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findUserByEmail(@PathVariable String email) {
        try {
            User byEmail = userService.findByEmail(email);
            HttpStatus httpStatus1 = byEmail!=null? HttpStatus.OK: HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(byEmail, httpStatus1);
        } catch (IncorrectEmailException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveUser(@RequestBody User user) {

        User savedUser = userService.save(user);

        HttpStatus httpStatus = savedUser != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(savedUser, httpStatus);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<Boolean> deleteUserByID(@PathVariable Integer id) {

        boolean delete = false;
        try {
            delete = userService.deleteUser(id);
            HttpStatus httpStatus = delete ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(delete, httpStatus);
        } catch (IncorrectIDException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity find(@PathVariable Integer id) throws Exception {

        UserMapper userMapper = new UserMapper(new ModelMapper());
        UserDto userDto = userMapper.mapTo(userService.findById(id).get());

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping(value = "login/{email}/{password}")
    public ResponseEntity<User> findByEmailAndPassword(@PathVariable String email, @PathVariable String password) {

        Optional<User> byEmailAndPassword = Optional.ofNullable(userService.findByEmailAndPassword(email, password));

        HttpStatus httpStatus = byEmailAndPassword.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(byEmailAndPassword.get(), httpStatus);
    }
}
