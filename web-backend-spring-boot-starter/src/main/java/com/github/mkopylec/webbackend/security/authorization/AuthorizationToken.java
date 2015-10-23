package com.github.mkopylec.webbackend.security.authorization;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthorizationToken extends AbstractAuthenticationToken {

    private Object principal;
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public AuthorizationToken() {
        super(new ArrayList<>());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
