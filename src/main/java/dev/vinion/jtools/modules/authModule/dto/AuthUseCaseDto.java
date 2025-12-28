package dev.vinion.jtools.modules.authModule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AuthUseCaseDto {
    String email;
    String password;
}
