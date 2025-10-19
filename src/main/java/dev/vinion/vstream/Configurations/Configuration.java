package dev.vinion.vstream.Configurations;

import dev.vinion.vstream.Middleware.AuthMiddleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@org.springframework.context.annotation.Configuration
public class Configuration implements WebMvcConfigurer {
    private final AuthMiddleware authMiddleware;

    @Autowired
    public Configuration(AuthMiddleware authMiddleware) {
        this.authMiddleware = authMiddleware;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authMiddleware).addPathPatterns("/**").excludePathPatterns("/user/create");
    }
}
