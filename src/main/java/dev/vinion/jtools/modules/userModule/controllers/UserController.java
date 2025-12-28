package dev.vinion.jtools.modules.userModule.controllers;

import dev.vinion.jtools.modules.userModule.dto.CreateUserControllerDto;
import dev.vinion.jtools.modules.userModule.dto.CreateUserUseCaseDto;
import dev.vinion.jtools.modules.userModule.usecases.CreateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {
    private final CreateUserUseCase createUserUseCase;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "/create")
    public void createUser(@Valid @RequestBody CreateUserControllerDto body) {
        CreateUserUseCaseDto data =
                CreateUserUseCaseDto.builder().email(body.getEmail()).password(body.getPassword()).build();

        this.createUserUseCase.execute(data);
    }
}
