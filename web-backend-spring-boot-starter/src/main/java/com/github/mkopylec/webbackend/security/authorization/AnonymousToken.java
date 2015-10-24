package com.github.mkopylec.webbackend.security.authorization;

import org.springframework.security.authentication.AnonymousAuthenticationToken;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class AnonymousToken extends AnonymousAuthenticationToken {

    public static final String NOT_AUTHORIZED_KEY = "notAuthorized";
    public static final String NOT_AUTHORIZED_ROLE = "NOT_AUTHORIZED";

    public AnonymousToken(Object principal) {
        super(NOT_AUTHORIZED_KEY, principal, createAuthorityList(NOT_AUTHORIZED_ROLE));
        setAuthenticated(false);
    }
}
