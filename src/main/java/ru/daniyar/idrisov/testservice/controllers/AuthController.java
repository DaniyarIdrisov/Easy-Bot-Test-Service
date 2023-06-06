package ru.daniyar.idrisov.testservice.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.daniyar.idrisov.testservice.dto.request.AccountRequest;
import ru.daniyar.idrisov.testservice.dto.request.JwtRequest;
import ru.daniyar.idrisov.testservice.dto.request.RefreshJwtRequest;
import ru.daniyar.idrisov.testservice.dto.response.AccountResponse;
import ru.daniyar.idrisov.testservice.dto.response.JwtResponse;
import ru.daniyar.idrisov.testservice.services.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PermitAll
    @PostMapping("/sign-up")
    public AccountResponse signUp(@RequestBody @Valid AccountRequest accountRequest) {
        return authService.signUp(accountRequest);
    }

    @PermitAll
    @PostMapping("/sign-in")
    public JwtResponse signIn(@RequestBody @Valid JwtRequest jwtRequest) {
        return authService.signIn(jwtRequest);
    }

    @PermitAll
    @PostMapping("/access")
    public JwtResponse getAccessToken(@RequestBody @Valid RefreshJwtRequest refreshJwtRequest) {
        return authService.getAccessToken(refreshJwtRequest);
    }

    @PermitAll
    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody @Valid RefreshJwtRequest refreshJwtRequest) {
        return authService.refresh(refreshJwtRequest);
    }

}
