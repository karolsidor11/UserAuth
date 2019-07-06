package pl.sidor.UserAuth.integratonTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.Role;
import models.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.sidor.UserAuth.repository.UserRepository;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerRestIntegrationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    //    todo do analizy
    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void shouldReturnUserByID() throws Exception {
        // given:
        Integer id = 1;
        User user = User.builder().id(id).name("admin").lastName("{noop}admin").role(new Role(1, "ADMIN")).build();
        String tokenForUser = createTokenForUser(user);

        MvcResult result = mockMvc.perform(get("/user/1")
                .header("Authorization", "Bearer " + tokenForUser)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // when:
        ResponseEntity<User> forEntity = testRestTemplate.getForEntity("/user/1", User.class);

        // then:
        assertEquals(HttpStatus.UNAUTHORIZED, forEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, forEntity.getHeaders().getContentType());

        verify(userRepository, times(1)).findById(id);
    }

    public static String createTokenForUser(User user) {

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("name", user.getName());
        objectMap.put("role", user.getRole().getRole());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .addClaims(objectMap)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 2000000))
                .signWith(SignatureAlgorithm.HS384, "token")
                .compact();
    }
}