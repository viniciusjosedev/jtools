package dev.vinion.vstream.Modules.UserModule.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserControllerDto {
    @NotBlank()
    @Email()
    private String email;

    @NotBlank()
    private String password;
}
