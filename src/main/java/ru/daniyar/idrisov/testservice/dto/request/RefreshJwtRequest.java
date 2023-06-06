package ru.daniyar.idrisov.testservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RefreshJwtRequest {

    @NotBlank
    private String refreshToken;

}
