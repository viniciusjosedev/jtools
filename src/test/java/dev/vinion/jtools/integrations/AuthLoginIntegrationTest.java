package dev.vinion.jtools.integrations;

import dev.vinion.jtools.database.entities.UserEntity.UserEntity;
import dev.vinion.jtools.database.repositories.UserRepository.UserRepository;
import dev.vinion.jtools.services.bcrypt.BCryptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

public class AuthLoginIntegrationTest extends IntegrationTestBase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BCryptService bCryptService;

    @BeforeEach
    public void clean() {
        this.userRepository.deleteAll();
    }

    @Test
    public void shouldGetTokenJwt() {
        String EMAIL = "test@test.com";
        String PASSWORD = "password";

        UserEntity createUser = UserEntity.builder().email(EMAIL).password(this.bCryptService.encode(PASSWORD)).build();

        this.userRepository.save(createUser);

        this.webTestClient.post()
                .uri("/auth/login")
                .bodyValue(Map.of("email", EMAIL, "password", PASSWORD))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.statusCode").isEqualTo(200)
                .jsonPath("$.data.token").exists();
    }
}
