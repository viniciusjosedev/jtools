package dev.vinion.jtools.modules.userModule.usecases;

import dev.vinion.jtools.database.entities.UserEntity.UserEntity;
import dev.vinion.jtools.database.repositories.UserRepository.UserRepository;
import dev.vinion.jtools.modules.userModule.dto.CreateUserUseCaseDto;
import dev.vinion.jtools.services.bcrypt.BCryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserRepository userRepository;
    private final BCryptService bCryptService;

    public void execute(CreateUserUseCaseDto data) {
        System.out.println("antes");

        Optional<UserEntity> findUser = this.userRepository.findByEmail(data.getEmail());

        if (findUser.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");

        System.out.println("depois");

        UserEntity user = UserEntity
                .builder()
                .email(data.getEmail())
                .password(this.bCryptService.encode(data.getPassword()))
                .build();

        System.out.printf("password %s", user.getPassword());

        this.userRepository.save(user);
    }
}
