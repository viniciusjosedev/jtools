package dev.vinion.jtools.modules.auth.controllers;

import dev.vinion.jtools.modules.auth.dto.AuthControllerDto;
import dev.vinion.jtools.modules.auth.dto.AuthUseCaseDto;
import dev.vinion.jtools.modules.auth.usecases.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthUseCase authUseCase;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthControllerDto body) {
        return this.authUseCase.execute(AuthUseCaseDto.builder().email(body.getEmail()).password(body.getPassword()).build());
    }
}
