package dev.vinion.vstream.Modules.UserModule.Controllers;

import dev.vinion.vstream.Database.Entities.UserEntity.UserEntity;
import dev.vinion.vstream.Database.Repositories.UserRepository.UserRepository;
import dev.vinion.vstream.Modules.UserModule.Dto.CreateUserControllerDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Optional;

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
        Optional<UserEntity> findUser = this.userRepository.findByEmail(body.getEmail());

        if (findUser.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exist");

        UserEntity userToCreate = UserEntity.builder().email(body.getEmail()).password(body.getPassword()).build();

        this.userRepository.save(userToCreate);
    }
}
