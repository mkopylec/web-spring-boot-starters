package com.github.mkopylec.webbackend.security.authorization;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

public class AuthorizationToken extends AbstractAuthenticationToken {

    public static final String UNKNOWN_PRINCIPAL = "Unknown";
    public static final String EXPIRED_FLAG = "Expired";
    public static final String NOT_EXPIRED_FLAG = "Not expired";

    private Object principal = UNKNOWN_PRINCIPAL;
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public AuthorizationToken() {
        super(new ArrayList<>());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public void setPrincipal(Object principal) {
        if (principal != null) {
            this.principal = principal;
        }
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
        if (isNotEmpty(authorities)) {
            this.authorities = authorities;
        }
    }
}
