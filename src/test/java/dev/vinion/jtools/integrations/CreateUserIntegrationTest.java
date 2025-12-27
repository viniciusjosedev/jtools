package dev.vinion.jtools.integrations;

import dev.vinion.jtools.database.entities.UserEntity.UserEntity;
import dev.vinion.jtools.database.repositories.UserRepository.UserRepository;
import dev.vinion.jtools.modules.userModule.dto.CreateUserControllerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.junit.jupiter.api.Assertions.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CreateUserIntegrationTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void clean() {
        this.userRepository.deleteAll();
    }

    @Test
    public void shouldCreateUser() {
        String EMAIL = "test@test.com";

        webTestClient
                .post()
                .uri("/user/create")
                .bodyValue(new CreateUserControllerDto("test@test.com", "password"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();

        Optional<UserEntity> findUser = this.userRepository.findByEmail(EMAIL);

        assertFalse(findUser.isEmpty());
    }

    @Test
    public void shouldGetBadRequestIfUserAlreadyExists() {
        String EMAIL = "test@test.com";
        String PASSWORD = "password";

        this.userRepository.save(UserEntity.builder().email(EMAIL).password(PASSWORD).build());

        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.BAD_REQUEST.value());
        response.put("success", false);
        response.put("data", Map.of("error", "User already exists"));

        webTestClient
                .post()
                .uri("/user/create")
                .bodyValue(new CreateUserControllerDto(EMAIL, PASSWORD))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                    .jsonPath("$.statusCode").isEqualTo(400)
                    .jsonPath("$.success").isEqualTo(false)
                    .jsonPath("$.data.error").isEqualTo("User already exists");

    }
}
