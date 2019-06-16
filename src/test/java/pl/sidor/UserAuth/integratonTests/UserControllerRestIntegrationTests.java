//package pl.sidor.UserAuth.integratonTests;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import models.Role;
//import models.User;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.*;
//import org.springframework.test.context.junit4.SpringRunner;
//import pl.sidor.UserAuth.repository.UserRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UserControllerRestIntegrationTests {
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//
//    @Before
//    public void setUp() throws Exception {
//
//    }
//
//    @Test
//     public void shouldReturnUserByID(){
//        // given:
//         Integer id=1;
//         User  user = User.builder()
//                 .id(id)
//                 .name("Karol")
//                 .lastName("Sidor")
//                 .email("karolsidor11@wp.pl")
//                 .build();
//
//         when(userRepository.findById(id)).thenReturn(Optional.of(user));
//
//
//        // when:
//         ResponseEntity<User> forEntity = testRestTemplate.getForEntity("/user/1", User.class);
//
//        // then:
//         assertEquals(HttpStatus.OK, forEntity.getStatusCode());
//         assertEquals(user,forEntity.getBody() );
//         assertEquals(MediaType.APPLICATION_JSON_UTF8, forEntity.getHeaders().getContentType());
//
//         verify(userRepository, times(1)).findById(id);
//     }
//
//     @Test
//     public  void shouldReturnAllUsers() throws JsonProcessingException {
//        //given:
//         when(userRepository.findAll()).thenReturn(getUsers());
//         String expected= objectMapper.writeValueAsString(getUsers());
//
//        //when:
//         ResponseEntity<String> forEntity = testRestTemplate.getForEntity("/findUsers", String.class);
//
//        //then:
//         assertEquals(HttpStatus.OK, forEntity.getStatusCode()) ;
//         assertEquals(expected, forEntity.getBody());
//
//         verify(userRepository, times(1)).findAll();
//     }
//
//     @Test
//     public  void shouldReturnNotFoundUserByID(){
//
//        //given:
//         Integer id = 9999;
//         when(userRepository.findById(id)).thenReturn(Optional.empty());
//
//        // when:
//         ResponseEntity<User> forEntity = testRestTemplate.getForEntity("/user/" + id, User.class);
//
//        //then:
//         assertEquals(HttpStatus.NOT_FOUND, forEntity.getStatusCode());
//
//     }
//     @Test
//     public void  shouldSaveAUserToDatabase(){
//        //given:
//         User user = User.builder()
//                 .id(1)
//                 .name("Jan")
//                 .lastName("Kowalski")
//                 .email("jan123@wp.pl" )
//                 .password("jan123")
//                 .build();
//
//         when(userRepository.save(user)).thenReturn(user);
//
//        //when:
//         ResponseEntity<User> userResponseEntity = testRestTemplate.postForEntity("/save", user, User.class);
//
//        // then:
//         assertEquals(HttpStatus.OK, userResponseEntity.getStatusCode());
//         assertEquals(user, userResponseEntity.getBody());
//
//         verify(userRepository, times(1)).save(any(User.class));
//     }
//
//     @Test
//     public void shouldDeleteUserByID(){
//
//        //given:
//         Integer id =1;
//
//         doNothing().when(userRepository).deleteById(id);
//
//         //when:
//         ResponseEntity<String> exchange = testRestTemplate.exchange("/delete/" + id, HttpMethod.DELETE, null, String.class);
//
//        //then:
//         assertEquals(HttpStatus.OK, exchange.getStatusCode());
//
//         verify(userRepository, times(1)).deleteById(id);
//     }
//
//     @Test
//     public  void  shouldReturnUserByEmail(){
//
//        // given:
//         String email="karolsidor11@p.pl";
//
//         when(userRepository.findByEmail(email)).thenReturn(getDefaultUser());
//
//        // when:
//         ResponseEntity<User> actualUser = testRestTemplate.getForEntity("/email/" + email, User.class);
//
//        //then:
//         assertEquals(HttpStatus.OK, actualUser.getStatusCode() );
//         assertEquals(getDefaultUser().getName(), actualUser.getBody().getName());
//
//         verify(userRepository, times(1)).findByEmail(anyString());
//     }
//
//     @Test
//     public void shouldLoginUser(){
//
//        //given:
//        String email= getDefaultUser().getEmail();
//        String password= getDefaultUser().getPassword();
//
//        when(userRepository.findByEmailAndPassword(email, password)).thenReturn(getDefaultUser());
//
//        // when:
//         ResponseEntity<User> forEntity = testRestTemplate.postForEntity("/login/" + email + "/" + password, getDefaultUser(), User.class);
//
//        //then:
//          assertEquals(HttpStatus.OK, forEntity.getStatusCode());
//          assertEquals(getDefaultUser().getName(), forEntity.getBody().getName());
//
//          verify(userRepository, times(1)).findByEmailAndPassword(email,password);
//     }
//
//     @Test
//     public void shouldGetUserDtoByID(){
//
//        // given:
//         int id = getDefaultUser().getId();
//          when(userRepository.findById(id)).thenReturn(Optional.of(getDefaultUser()));
//
//        //when:
//         ResponseEntity<User> forEntity = testRestTemplate.getForEntity("/get/" + id, User.class);
//
//        //then:
//          assertEquals(HttpStatus.OK, forEntity.getStatusCode());
//          assertEquals(getDefaultUser().getName(), forEntity.getBody().getName());
//
//          verify(userRepository, times(1)).findById(id);
//     }
//
//     private List<User> getUsers(){
//        List<User>  userList = new ArrayList<>();
//        User user =User.builder()
//                .id(1)
//                .name("Karol")
//                .lastName("Sidor")
//                .email("karolsidor11@wp.pl")
//                .password("karol1234")
//                .build();
//
//         User user1 = User.builder()
//                 .id(2)
//                 .name("Jan")
//                 .lastName("Nowak")
//                 .name("nowak@wp.pl")
//                 .password("nowak1234")
//                 .build();
//
//         userList.add(user);
//         userList.add(user1);
//         return  userList;
//     }
//
//     public User getDefaultUser(){
//       return User.builder()
//                .id(1)
//                .name("Karol")
//                .lastName("Sidor")
//                .email("karolsidor11@wp.pl")
//                .password("karol1234")
//                .role(new Role(1, "USER")).build();
//     }
//}
