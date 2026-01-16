package dev.vinion.jtools.unit;

import dev.vinion.jtools.database.entities.UserEntity.UserEntity;
import dev.vinion.jtools.database.repositories.UserRepository.UserRepository;
import dev.vinion.jtools.modules.user.dto.CreateUserUseCaseDto;
import dev.vinion.jtools.modules.user.usecases.CreateUserUseCase;
import dev.vinion.jtools.services.bcrypt.BCryptService;
import org.junit.jupiter.api.Test; // JUnit 5
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {
    @Mock
    UserRepository userRepository;

    @Mock
    BCryptService bCryptService;

    @InjectMocks
    CreateUserUseCase createUserUseCase;

    @Test
    public void shouldCreateUser() {
        String email = "test@test.com";
        String password = "password";
        String HASH_PASSWORD = "HASH_PASSWORD";

        CreateUserUseCaseDto createUser =
                CreateUserUseCaseDto.builder().email(email).password(password).build();

        when(userRepository.findByEmail(createUser.getEmail())).thenReturn(Optional.empty());
        when(bCryptService.encode(createUser.getPassword())).thenReturn(HASH_PASSWORD);

        ArgumentCaptor<UserEntity> userEntityCaptor =
                ArgumentCaptor.forClass(UserEntity.class);

        createUserUseCase.execute(createUser);

        verify(userRepository).save(userEntityCaptor.capture());

        UserEntity userEntity = userEntityCaptor.getValue();

        assertEquals(email, userEntity.getEmail());
        assertEquals(HASH_PASSWORD, userEntity.getPassword());
    }

    @Test
    public void shouldThrowExceptionWhenUserAlreadyExists() {
        String email = "test@test.com";
        String password = "password";

        CreateUserUseCaseDto createUser =
                CreateUserUseCaseDto.builder().email(email).password(password).build();

        when(userRepository.findByEmail(createUser.getEmail())).thenReturn(Optional.of(new UserEntity()));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            createUserUseCase.execute(createUser);
        });

        assertEquals("User already exists", ex.getReason());
        verify(userRepository, never()).save(any());
        verify(bCryptService, never()).encode(any());
    }
}
