package jtools.unit;

import dev.vinion.jtools.database.entities.UserEntity.UserEntity;
import dev.vinion.jtools.database.repositories.UserRepository.UserRepository;
import dev.vinion.jtools.modules.userModule.dto.CreateUserUseCaseDto;
import dev.vinion.jtools.modules.userModule.usecases.CreateUserUseCase;
import org.junit.jupiter.api.Test; // JUnit 5
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @InjectMocks
    CreateUserUseCase createUserUseCase;

    @Captor
    ArgumentCaptor<UserEntity> userCaptor;

    @Test
    public void shouldCreateUser() {
        String email = "test@test.com";
        String password = "password";

        CreateUserUseCaseDto createUser =
                CreateUserUseCaseDto.builder().email(email).password(password).build();

        when(userRepository.findByEmail(createUser.getEmail())).thenReturn(Optional.empty());

        ArgumentCaptor<UserEntity> userCaptor =
                ArgumentCaptor.forClass(UserEntity.class);

        createUserUseCase.execute(createUser);

        verify(userRepository).save(userCaptor.capture());

        UserEntity userEntity = userCaptor.getValue();

        assertEquals(email, userEntity.getEmail());
        assertEquals(password, userEntity.getPassword());
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

        assertEquals("User already exist", ex.getReason());
    }
}
