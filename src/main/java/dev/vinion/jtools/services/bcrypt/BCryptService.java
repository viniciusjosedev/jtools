package dev.vinion.jtools.services.bcrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptService {
    private final PasswordEncoder encoder;

    BCryptService() {
        this.encoder = new BCryptPasswordEncoder();
    }

    public String encode(String password) {
        return this.encoder.encode(password);
    }

    public Boolean matches(String password, String hash) {
        return this.encoder.matches(password, hash);
    }
}
