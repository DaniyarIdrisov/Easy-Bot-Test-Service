package ru.daniyar.idrisov.testservice.models.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    USER("USER");

    public final String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

}
