package pl.sidor.UserAuth.integratonTests;

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
import pl.sidor.UserAuth.repository.UserRepository;
import java.util.Optional;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest()
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