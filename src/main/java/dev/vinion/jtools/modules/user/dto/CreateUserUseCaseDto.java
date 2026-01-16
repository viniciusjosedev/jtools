package dev.vinion.jtools.modules.user.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateUserUseCaseDto {
    private String email;
    private String password;
}
