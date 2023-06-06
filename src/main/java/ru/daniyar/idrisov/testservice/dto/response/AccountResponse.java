package ru.daniyar.idrisov.testservice.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.daniyar.idrisov.testservice.models.enums.Role;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private UUID id;

    private Instant createdAt;

    private Instant updatedAt;

    private String email;

    private String firstName;

    private String lastName;

    private Set<Role> roles;

}
