package pl.sidor.UserAuth.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserToken {

    public static String createTokenForUser(User user){

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("name", user.getName());
        objectMap.put("role", user.getRole().getRole());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .addClaims(objectMap)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+2000000))
                .signWith(SignatureAlgorithm.HS384, "token")
                .compact();
    }
}
