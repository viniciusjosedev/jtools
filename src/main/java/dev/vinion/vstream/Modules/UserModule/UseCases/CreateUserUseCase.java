package dev.vinion.vstream.Modules.UserModule.UseCases;

import dev.vinion.vstream.Database.Entities.UserEntity.UserEntity;
import dev.vinion.vstream.Database.Repositories.UserRepository.UserRepository;
import dev.vinion.vstream.Modules.UserModule.Dto.CreateUserUseCaseDto;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class CreateUserUseCase {
    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(CreateUserUseCaseDto data) {
        Optional<UserEntity> findUser = this.userRepository.findByEmail(data.getEmail());

        if (findUser.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exist");

        UserEntity user = UserEntity.builder().email(data.getEmail()).password(data.getPassword()).build();

        this.userRepository.save(user);
    }
}
