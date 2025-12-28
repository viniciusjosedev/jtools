package dev.vinion.jtools.services.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.vinion.jtools.database.entities.UserEntity.UserEntity;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Component
public class JwtService {
    final Algorithm algorithm;

    public JwtService() {
        this.algorithm = Algorithm.HMAC256("test");
    }

    public String sign(UserEntity data) {
        JWTCreator.Builder sign = JWT.create();
        sign.withExpiresAt(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant())
                .withClaim("id", data.getId())
                .withClaim("email", data.getEmail());

        return sign.sign(this.algorithm);
    }

    public Map<String, Claim> verify(String token) throws JWTVerificationException {
        DecodedJWT decoded = JWT.require(this.algorithm).build().verify(token);

        return decoded.getClaims();
    }
}
