package dev.vinion.jtools.modules.userModule.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateUserUseCaseDto {
    private String email;
    private String password;
}
