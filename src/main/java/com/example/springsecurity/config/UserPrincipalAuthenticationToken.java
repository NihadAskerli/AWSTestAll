package com.example.springsecurity.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {
    private final UserPrinciple principle;
    public UserPrincipalAuthenticationToken(UserPrinciple principle) {
        super(principle.getAuthorities());
        this.principle=principle;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrinciple getPrincipal() {
        return principle;
    }
}
