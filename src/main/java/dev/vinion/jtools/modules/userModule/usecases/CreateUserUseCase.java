package dev.vinion.jtools.modules.userModule.usecases;

import dev.vinion.jtools.database.entities.UserEntity.UserEntity;
import dev.vinion.jtools.database.repositories.UserRepository.UserRepository;
import dev.vinion.jtools.modules.userModule.dto.CreateUserUseCaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserRepository userRepository;

    public void execute(CreateUserUseCaseDto data) {
        Optional<UserEntity> findUser = this.userRepository.findByEmail(data.getEmail());

        if (findUser.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exist");

        UserEntity user = UserEntity.builder().email(data.getEmail()).password(data.getPassword()).build();

        this.userRepository.save(user);
    }
}
