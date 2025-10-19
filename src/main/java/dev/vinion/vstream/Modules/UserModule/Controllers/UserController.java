package dev.vinion.vstream.Modules.UserModule.Controllers;

import dev.vinion.vstream.Database.Entities.UserEntity.UserEntity;
import dev.vinion.vstream.Database.Repositories.UserRepository.UserRepository;
import dev.vinion.vstream.Modules.UserModule.Dto.CreateUserControllerDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping(path = "/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/create")
    public UserEntity getUser(@Valid @RequestBody CreateUserControllerDto body) {
        UserEntity user = UserEntity.builder().email(body.getEmail()).password(body.getPassword()).build();

        System.out.println(body.getEmail());

//        this.userRepository(user)

        return user;
    }
}
