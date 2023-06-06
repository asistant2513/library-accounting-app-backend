package com.havrylenko.library.model.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, LIBRARIAN, READER, GUEST;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
