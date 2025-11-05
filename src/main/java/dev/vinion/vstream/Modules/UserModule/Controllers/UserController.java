package dev.vinion.vstream.Modules.UserModule.Controllers;

import dev.vinion.vstream.Database.Entities.UserEntity.UserEntity;
import dev.vinion.vstream.Database.Repositories.UserRepository.UserRepository;
import dev.vinion.vstream.Modules.UserModule.Dto.CreateUserControllerDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController()
@RequestMapping(path = "/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/create")
    public void createUser(@Valid @RequestBody CreateUserControllerDto body) {
        UserEntity user = UserEntity.builder().email(body.getEmail()).password(body.getPassword()).build();
        Object userFind = this.userRepository.findOne(Example.of(user));

        this.userRepository.save(user);

    }
}
