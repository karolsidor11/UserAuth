package pl.sidor.UserAuth.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sidor.UserAuth.service.UserService;
import pl.sidor.UserAuth.token.UserToken;

import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login() throws UnsupportedEncodingException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Map<String, Object> map= new HashMap<>();
        map.put("roles", authorities);
        map.put("name", name);

        return Jwts.builder()
               .setSubject(name)
               .addClaims(map)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+20000000))
                .signWith(SignatureAlgorithm.HS256,"ZG)KB01hS9LsTuv".getBytes("UTF-8"))
                .compact();
    }
}
