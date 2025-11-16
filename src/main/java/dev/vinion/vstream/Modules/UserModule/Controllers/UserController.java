package dev.vinion.vstream.Modules.UserModule.Controllers;

import dev.vinion.vstream.Modules.UserModule.Dto.CreateUserControllerDto;
import dev.vinion.vstream.Modules.UserModule.Dto.CreateUserUseCaseDto;
import dev.vinion.vstream.Modules.UserModule.UseCases.CreateUserUseCase;
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
