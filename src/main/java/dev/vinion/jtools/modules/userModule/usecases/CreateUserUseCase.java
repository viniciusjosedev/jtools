package dev.vinion.jtools.modules.userModule.usecases;

import dev.vinion.jtools.database.entities.UserEntity.UserEntity;
import dev.vinion.jtools.database.repositories.UserRepository.UserRepository;
import dev.vinion.jtools.modules.userModule.dto.CreateUserUseCaseDto;
import dev.vinion.jtools.services.bcrypt.BCryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserRepository userRepository;
    private final BCryptService bCryptService;

    public void execute(CreateUserUseCaseDto data) {
        Optional<UserEntity> findUser = this.userRepository.findByEmail(data.getEmail());

        if (findUser.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");

        UserEntity user = UserEntity
                .builder()
                .email(data.getEmail())
                .password(this.bCryptService.encode(data.getPassword()))
                .build();

        this.userRepository.save(user);
    }
}
