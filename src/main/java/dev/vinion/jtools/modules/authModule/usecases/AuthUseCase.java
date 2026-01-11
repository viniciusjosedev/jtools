package dev.vinion.jtools.modules.authModule.usecases;

import dev.vinion.jtools.database.entities.UserEntity.UserEntity;
import dev.vinion.jtools.database.repositories.UserRepository.UserRepository;
import dev.vinion.jtools.modules.authModule.dto.AuthUseCaseDto;
import dev.vinion.jtools.services.jwt.JwtService;
import dev.vinion.jtools.services.bcrypt.BCryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUseCase {
    private final UserRepository userRepository;
    private final BCryptService bCryptService;
    private final JwtService jwtService;

    public ResponseEntity<?> execute(AuthUseCaseDto data) {
        Optional<UserEntity> findUser = this.userRepository.findByEmail(data.getEmail());

        if (findUser.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        if (!bCryptService.matches(data.getPassword(), findUser.get().getPassword())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");

        return ResponseEntity.ok(Map.of("token", this.jwtService.sign(findUser.get())));
    }
}
