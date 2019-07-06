package pl.sidor.UserAuth.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import models.Role
import models.User
import spock.lang.Specification

class UserTokenTest extends Specification {

    private UserToken token

    void setup() {
        token = new UserToken()
    }

    def "should return token "() {

        when:
        String actualToken = token.createTokenForUser(getDeafulrUser())
        Claims claims = Jwts.parser().setSigningKey("token").parseClaimsJws(actualToken).getBody()

        then:
        !actualToken.isEmpty()
        claims.get("name") == "Karol"
        claims.get("role") == "ADMIN"

    }

    private static User getDeafulrUser() {
        return User.builder()
                .id(1)
                .name("Karol")
                .lastName("Sidor")
                .email("karolsidor11@wp.pl")
                .role(new Role(1, "ADMIN"))
                .build()

    }
}
