package pl.sidor.UserAuth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Role;
import models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sidor.UserAuth.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTestIntegration {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        User user = User.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .password("null")
                .role(new Role()).build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("karolsidor11@wp.pl")).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(savedUser());
        doNothing().when(userRepository).deleteUserById(1);
    }

    @Test
    public void shouldReturnUserByID() throws Exception {
        mockMvc.perform(get("/user/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Karol")))
                .andExpect(jsonPath("$.lastName", is("Sidor")))
                .andExpect(jsonPath("$.password", is("null")))
                .andExpect(jsonPath("$.role.id", is(0)));

        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void userNotFound() throws  Exception{
        mockMvc.perform(get("/user/99"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnUserByEmail() throws Exception{
        mockMvc.perform(get("/email/karolsidor11@wp.pl"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Karol")))
                .andExpect(jsonPath("$.lastName", is("Sidor")))
                .andExpect(jsonPath("$.password", is("null")))
                .andExpect(jsonPath("$.role.id", is(0)));

        verify(userRepository, timeout(2)).findByEmail("karolsidor11@wp.pl");
    }

    @Test
    public void shouldSaveUser() throws  Exception{
        mockMvc.perform(post("/save")
                .content(OBJECT_MAPPER.writeValueAsBytes(savedUser()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Jan")))
                .andExpect(jsonPath("$.lastName", is("Kowalski")))
                .andExpect(jsonPath("$.email", is("jankowalski@wp.pl")))
                .andExpect(jsonPath("$.password", is("jan123")));

        verify(userRepository, timeout(1)).save(savedUser());
    }

    @Test
    public void saveEmptyUser() throws Exception{

        String userInJson = "{\"id\":\"name\":\"lastName\"}";

        mockMvc.perform(post("/save")
        .content(userInJson)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(userRepository, times(0)).save(any(User.class));

    }

    @Test
    public void shouldDeleteUserById() throws Exception{
        mockMvc.perform(delete("/delete/1"))
                .andExpect(status().isOk());

        verify(userRepository, timeout(1)).deleteUserById(1);
    }

    @Test
    public void shouldReturnAllUsers() throws Exception{

        when(userRepository.findAll()).thenReturn(userList());

        mockMvc.perform(get("/findUsers"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$.[0].name", is("Paweł")))
                .andExpect(jsonPath("$.[0].lastName", is("Kowalski")))
                .andExpect(jsonPath("$.[0].email", is("kowalski@wp.pl")))
                .andExpect(jsonPath("$.[1].id", is(1)))
                .andExpect(jsonPath("$.[1].name", is("Jan")))
                .andExpect(jsonPath("$.[1].lastName", is("Nowak")))
                .andExpect(jsonPath("$.[1].email", is("nowak@wp.pl")));

        verify(userRepository, timeout(1)).findAll();

    }

    @Test
    public void shouldReturnUserByEmailAndPassword() throws  Exception{
        String email="piotr@wp.pl";
        String password="piotr123";

        User user= User.builder()
                .id(12)
                .name("Piotr")
                .lastName("Nowak")
                .email(email)
                .password(password)
                .build();

        when(userRepository.findByEmailAndPassword(email, password)).thenReturn(user);

        mockMvc.perform(post("/login/"+email+"/"+password))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(12)))
                .andExpect(jsonPath("$.name", is("Piotr")))
                .andExpect(jsonPath("$.lastName", is("Nowak")))
                .andExpect(jsonPath("$.email", is("piotr@wp.pl")));

        verify(userRepository, timeout(1)).findByEmailAndPassword(email, password);

    }

    private List<User> userList(){
        List<User> users = new ArrayList<>();
        User user = User.builder()
                .id(1)
                .name("Jan")
                .lastName("Nowak")
                .email("nowak@wp.pl")
                .password("nowak123")
                .build();

        User user1 = User.builder()
                .id(2)
                .name("Paweł")
                .lastName("Kowalski")
                .email("kowalski@wp.pl")
                .password("kowalski123")
                .build();
        users.add(user1);
        users.add(user);
        return  users;
    }

    private User savedUser(){
        return User.builder()
                .id(2)
                .name("Jan")
                .lastName("Kowalski")
                .email("jankowalski@wp.pl")
                .password("jan123")
                .build();
    }
}