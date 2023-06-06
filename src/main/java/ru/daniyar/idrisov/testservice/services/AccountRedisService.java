package ru.daniyar.idrisov.testservice.services;

import ru.daniyar.idrisov.testservice.models.jpa.AccountEntity;

public interface AccountRedisService {

    void addRefreshTokenToUser(AccountEntity accountEntity, String refreshToken);

    void deleteOldAndAddNewRefreshToken(AccountEntity accountEntity, String refreshToken);

    boolean checkForRefreshTokenExisting(String email, String refreshToken);

}
