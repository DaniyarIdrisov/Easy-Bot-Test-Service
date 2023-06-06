package ru.daniyar.idrisov.testservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.daniyar.idrisov.testservice.dto.response.AccountResponse;
import ru.daniyar.idrisov.testservice.services.AccountService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{id}")
    public AccountResponse getAccountById(@PathVariable UUID id) {
        return accountService.getAccountById(id);
    }

}
