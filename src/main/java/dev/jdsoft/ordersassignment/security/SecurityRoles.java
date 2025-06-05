package dev.jdsoft.ordersassignment.security;

import org.springframework.security.core.GrantedAuthority;

public enum SecurityRoles implements GrantedAuthority {

    ADMIN, USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
