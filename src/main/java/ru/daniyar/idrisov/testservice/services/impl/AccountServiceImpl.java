package ru.daniyar.idrisov.testservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.daniyar.idrisov.testservice.exceptions.EntityNotFoundException;
import ru.daniyar.idrisov.testservice.dto.response.AccountResponse;
import ru.daniyar.idrisov.testservice.models.mappers.AccountMapper;
import ru.daniyar.idrisov.testservice.repositories.jpa.AccountRepository;
import ru.daniyar.idrisov.testservice.services.AccountService;

import java.util.UUID;

import static ru.daniyar.idrisov.testservice.exceptions.constants.ExceptionConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(UUID id) {
        var accountEntity = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE));
        return accountMapper.toAccountResponse(accountEntity);
    }

}
