package dev.vinion.jtools.modules.authModule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthControllerDto {
    @NotBlank
    @Email
    String email;

    @NotBlank
    String password;
}
