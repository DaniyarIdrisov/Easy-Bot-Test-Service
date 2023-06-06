package ru.daniyar.idrisov.testservice.services;

import ru.daniyar.idrisov.testservice.dto.request.AccountRequest;
import ru.daniyar.idrisov.testservice.dto.request.JwtRequest;
import ru.daniyar.idrisov.testservice.dto.request.RefreshJwtRequest;
import ru.daniyar.idrisov.testservice.dto.response.AccountResponse;
import ru.daniyar.idrisov.testservice.dto.response.JwtResponse;

public interface AuthService {

    AccountResponse signUp(AccountRequest accountRequest);

    JwtResponse signIn(JwtRequest jwtRequest);

    JwtResponse getAccessToken(RefreshJwtRequest refreshJwtRequest);

    JwtResponse refresh(RefreshJwtRequest refreshJwtRequest);

}
