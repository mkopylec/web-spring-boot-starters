package com.github.mkopylec.webbackend.security.authorization;

import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.github.mkopylec.webbackend.security.authorization.AuthorizationToken.NOT_EXPIRED_FLAG;
import static org.slf4j.LoggerFactory.getLogger;

public class TokenAuthorizationProvider implements AuthenticationProvider {

    private static final Logger log = getLogger(TokenAuthorizationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object expiredFlag = authentication.getDetails();
        if (expiredFlag == NOT_EXPIRED_FLAG) {
            authentication.setAuthenticated(true);
        } else {
            authentication = new AnonymousToken(authentication.getPrincipal());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Token authorization complete: {}", authentication);

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthorizationToken.class.isAssignableFrom(authentication);
    }
}
