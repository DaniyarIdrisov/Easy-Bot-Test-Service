package ru.daniyar.idrisov.testservice.security.providers;

import io.jsonwebtoken.Claims;
import ru.daniyar.idrisov.testservice.models.jpa.AccountEntity;

public interface JwtProvider {

    String generateAccessToken(AccountEntity accountEntity);

    String generateRefreshToken(AccountEntity accountEntity);

    boolean validateAccessToken(String accessToken);

    boolean validateRefreshToken(String refreshToken);

    Claims getAccessClaims(String token);

    Claims getRefreshClaims(String token);

}
