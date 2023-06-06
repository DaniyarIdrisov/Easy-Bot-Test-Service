package ru.daniyar.idrisov.testservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.daniyar.idrisov.testservice.exceptions.EntityNotFoundException;
import ru.daniyar.idrisov.testservice.models.jpa.AccountEntity;
import ru.daniyar.idrisov.testservice.models.redis.AccountRedis;
import ru.daniyar.idrisov.testservice.repositories.redis.AccountRedisRepository;
import ru.daniyar.idrisov.testservice.services.AccountRedisService;

import java.util.Collections;

import static ru.daniyar.idrisov.testservice.exceptions.constants.ExceptionConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountRedisServiceImpl implements AccountRedisService {

    private final AccountRedisRepository accountRedisRepository;

    @Override
    @Transactional
    public void addRefreshTokenToUser(AccountEntity accountEntity, String refreshToken) {
        var optionalAccountRedis = accountRedisRepository.findById(accountEntity.getEmail());
        AccountRedis accountRedis;
        if (optionalAccountRedis.isPresent()) {
            accountRedis = optionalAccountRedis.get();
            accountRedis.getTokens().add(refreshToken);
        } else {
            accountRedis = createRedisAccount(accountEntity, refreshToken);
        }
        accountRedisRepository.save(accountRedis);
    }

    @Override
    @Transactional
    public void deleteOldAndAddNewRefreshToken(AccountEntity accountEntity, String refreshToken) {
        var optionalAccountRedis = accountRedisRepository.findById(accountEntity.getEmail());
        AccountRedis accountRedis;
        if (optionalAccountRedis.isPresent()) {
            accountRedis = optionalAccountRedis.get();
            accountRedis.getTokens().clear();
            accountRedis.setTokens(Collections.singletonList(refreshToken));
        } else {
            accountRedis = createRedisAccount(accountEntity, refreshToken);
        }
        accountRedisRepository.save(accountRedis);
    }

    private AccountRedis createRedisAccount(AccountEntity accountEntity, String refreshToken) {
        return AccountRedis.builder()
                .id(accountEntity.getEmail())
                .tokens(Collections.singletonList(refreshToken))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkForRefreshTokenExisting(String email, String refreshToken) {
        var accountRedis = accountRedisRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException(ACCOUNT_WITH_THIS_EMAIL_NOT_FOUND_MESSAGE, email));
        return accountRedis.getTokens().contains(refreshToken);
    }

}
