package dev.vinion.vstream.Middleware;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import dev.vinion.vstream.Services.Jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Map;

@Component
public class AuthMiddleware implements HandlerInterceptor {
    JwtService jwtService;

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
            try {
                response.setContentType("application/json");
                response.getWriter().write("nao deu seu pika");
                response.getWriter().flush();
            } catch (IOException IoErr) {
                System.out.printf("Err in send json to user in auth: %s\n", IoErr.getMessage());
            }

            return false;
        }
    }
}
