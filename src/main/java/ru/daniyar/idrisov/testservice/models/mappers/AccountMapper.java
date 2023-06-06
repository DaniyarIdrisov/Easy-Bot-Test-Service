package ru.daniyar.idrisov.testservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import ru.daniyar.idrisov.testservice.dto.request.AccountRequest;
import ru.daniyar.idrisov.testservice.dto.response.AccountResponse;
import ru.daniyar.idrisov.testservice.models.jpa.AccountEntity;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountResponse toAccountResponse(AccountEntity accountEntity);

    AccountEntity toAccountEntity(AccountRequest accountRequest);

}
