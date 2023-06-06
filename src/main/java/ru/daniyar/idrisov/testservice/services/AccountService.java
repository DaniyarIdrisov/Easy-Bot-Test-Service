package ru.daniyar.idrisov.testservice.services;

import ru.daniyar.idrisov.testservice.dto.response.AccountResponse;

import java.util.UUID;

public interface AccountService {

    AccountResponse getAccountById(UUID id);

}
