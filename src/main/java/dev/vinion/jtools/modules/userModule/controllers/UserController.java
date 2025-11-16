package dev.vinion.jtools.modules.userModule.controllers;

import dev.vinion.jtools.modules.userModule.dto.CreateUserControllerDto;
import dev.vinion.jtools.modules.userModule.dto.CreateUserUseCaseDto;
import dev.vinion.jtools.modules.userModule.usecases.CreateUserUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/user")
public class UserController {
    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping(path = "/create")
    public void createUser(@Valid @RequestBody CreateUserControllerDto body) {
        CreateUserUseCaseDto data =
                CreateUserUseCaseDto.builder().email(body.getEmail()).password(body.getPassword()).build();

        this.createUserUseCase.execute(data);
    }
}
