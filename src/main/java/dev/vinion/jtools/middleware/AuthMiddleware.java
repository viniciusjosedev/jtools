package dev.vinion.jtools.middleware;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import dev.vinion.jtools.services.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Map;

@Component
public class AuthMiddleware implements HandlerInterceptor {
    private final JwtService jwtService;

    @Autowired
    public AuthMiddleware(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object _handler) {
        try {
            Map<String, Claim> data = this.jwtService.verify(request.getHeader("authorization"));

            request.setAttribute("userId", data.get("userId"));

            return true;
        } catch (JWTVerificationException err) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
    }
}
