package dev.vinion.vstream.Modules.UserModule.Dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateUserUseCaseDto {
    private String email;
    private String password;
}
