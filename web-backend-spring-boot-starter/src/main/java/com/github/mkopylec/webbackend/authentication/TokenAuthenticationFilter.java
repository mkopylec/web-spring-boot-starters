package com.github.mkopylec.webbackend.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public TokenAuthenticationFilter() {
        super(new AntPathRequestMatcher("/**"));
        setAuthenticationSuccessHandler(new EmptyAuthenticationHandler());
        setContinueChainBeforeSuccessfulAuthentication(true);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        //TODO real authorization
        PreAuthenticatedAuthenticationToken testUser = new PreAuthenticatedAuthenticationToken("test_user", null);
        testUser.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(testUser);
        return testUser;
    }
}
