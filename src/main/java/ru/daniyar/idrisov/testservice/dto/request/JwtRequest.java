package ru.daniyar.idrisov.testservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class JwtRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
