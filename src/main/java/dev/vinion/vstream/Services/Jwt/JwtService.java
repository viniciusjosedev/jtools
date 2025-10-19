package dev.vinion.vstream.Services.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Component
public class JwtService {
    final Algorithm algorithm;

    public JwtService() {
        this.algorithm = Algorithm.HMAC256("test");
    }

    public String sign(Map<String, Object> data) {
        JWTCreator.Builder sign = JWT.create();
        sign.withExpiresAt(new Date(2025, 12, 12));

        data.keySet().forEach((key) -> {
            sign.withClaim(key, (String) data.get(key));
        });

        return sign.sign(this.algorithm);
    }

    public Map<String, Claim> verify(String token) throws JWTVerificationException {
        DecodedJWT decoded = JWT.require(this.algorithm).build().verify(token);

        return decoded.getClaims();
    }
}
