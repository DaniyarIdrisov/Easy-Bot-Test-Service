package ru.daniyar.idrisov.testservice.services.impl;

import io.jsonwebtoken.JwtException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.daniyar.idrisov.testservice.dto.request.RefreshJwtRequest;
import ru.daniyar.idrisov.testservice.exceptions.ServiceBadRequestException;
import ru.daniyar.idrisov.testservice.exceptions.EntityConflictException;
import ru.daniyar.idrisov.testservice.dto.request.AccountRequest;
import ru.daniyar.idrisov.testservice.dto.request.JwtRequest;
import ru.daniyar.idrisov.testservice.dto.response.AccountResponse;
import ru.daniyar.idrisov.testservice.dto.response.JwtResponse;
import ru.daniyar.idrisov.testservice.models.enums.Role;
import ru.daniyar.idrisov.testservice.models.jpa.AccountEntity;
import ru.daniyar.idrisov.testservice.models.mappers.AccountMapper;
import ru.daniyar.idrisov.testservice.repositories.jpa.AccountRepository;
import ru.daniyar.idrisov.testservice.services.AccountRedisService;
import ru.daniyar.idrisov.testservice.services.AuthService;
import ru.daniyar.idrisov.testservice.security.providers.JwtProvider;

import java.util.Collections;

import static ru.daniyar.idrisov.testservice.exceptions.constants.ExceptionConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AccountRedisService accountRedisService;

    @Override
    @Transactional
    public AccountResponse signUp(@NotNull AccountRequest accountRequest) {
        checkForEmptyFields(accountRequest);
        checkForEmailExisting(accountRequest);
        var accountEntity = accountMapper.toAccountEntity(accountRequest);
        var hashPassword = passwordEncoder.encode(accountRequest.getPassword());
        accountEntity.setHashPassword(hashPassword);
        accountEntity.setRoles(Collections.singleton(Role.USER));
        return accountMapper.toAccountResponse(accountRepository.save(accountEntity));
    }

    private void checkForEmptyFields(AccountRequest accountRequest) {
        if (accountRequest.getEmail() == null) {
            throw new ServiceBadRequestException(EMPTY_FIELD_MESSAGE, "email");
        }
        if (accountRequest.getLastName() == null) {
            throw new ServiceBadRequestException(EMPTY_FIELD_MESSAGE, "lastName");
        }
        if (accountRequest.getFirstName() == null) {
            throw new ServiceBadRequestException(EMPTY_FIELD_MESSAGE, "firstName");
        }
        if (accountRequest.getPassword() == null) {
            throw new ServiceBadRequestException(EMPTY_FIELD_MESSAGE, "password");
        }
    }

    private void checkForEmailExisting(AccountRequest accountRequest) {
        if (accountRepository.findByEmail(accountRequest.getEmail()).isPresent()) {
            throw new EntityConflictException(ACCOUNT_WITH_THIS_EMAIL_EXISTS_MESSAGE, accountRequest.getEmail());
        }
    }

    @Override
    @Transactional
    public JwtResponse signIn(@NotNull JwtRequest jwtRequest) {
        var accountEntity = accountRepository.findByEmail(jwtRequest.getEmail())
                .orElseThrow(() -> new ServiceBadRequestException(INVALID_LOGIN_OR_PASSWORD_MESSAGE));
        checkForPasswordsMatches(accountEntity, jwtRequest);
        var accessToken = jwtProvider.generateAccessToken(accountEntity);
        var refreshToken = jwtProvider.generateRefreshToken(accountEntity);
        accountRedisService.addRefreshTokenToUser(accountEntity, refreshToken);
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void checkForPasswordsMatches(AccountEntity accountEntity, JwtRequest jwtRequest) {
        if (!passwordEncoder.matches(jwtRequest.getPassword(), accountEntity.getHashPassword())) {
            throw new ServiceBadRequestException(INVALID_LOGIN_OR_PASSWORD_MESSAGE);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public JwtResponse getAccessToken(@NotNull RefreshJwtRequest refreshJwtRequest) {
        checkRefreshTokenForValidity(refreshJwtRequest.getRefreshToken());
        var claims = jwtProvider.getRefreshClaims(refreshJwtRequest.getRefreshToken());
        var email = claims.getSubject();
        checkForRefreshTokenExisting(email, refreshJwtRequest.getRefreshToken());
        var accountEntity = accountRepository.findByEmail(email)
                .orElseThrow(() -> new JwtException(INVALID_REFRESH_TOKEN_MESSAGE));
        var accessToken = jwtProvider.generateAccessToken(accountEntity);
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshJwtRequest.getRefreshToken())
                .build();
    }

    private void checkForRefreshTokenExisting(String email, String refreshToken) {
        if (!accountRedisService.checkForRefreshTokenExisting(email, refreshToken)) {
            throw new JwtException(INVALID_REFRESH_TOKEN_MESSAGE);
        }
    }

    private void checkRefreshTokenForValidity(String refreshToken) {
        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw new JwtException(INVALID_REFRESH_TOKEN_MESSAGE);
        }
    }

    @Override
    @Transactional
    public JwtResponse refresh(@NotNull RefreshJwtRequest refreshJwtRequest) {
        checkRefreshTokenForValidity(refreshJwtRequest.getRefreshToken());
        var claims = jwtProvider.getRefreshClaims(refreshJwtRequest.getRefreshToken());
        var email = claims.getSubject();
        checkForRefreshTokenExisting(email, refreshJwtRequest.getRefreshToken());
        var accountEntity = accountRepository.findByEmail(email)
                .orElseThrow(() -> new JwtException(INVALID_REFRESH_TOKEN_MESSAGE));
        var accessToken = jwtProvider.generateAccessToken(accountEntity);
        var newRefreshToken = jwtProvider.generateRefreshToken(accountEntity);
        accountRedisService.deleteOldAndAddNewRefreshToken(accountEntity, newRefreshToken);
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
