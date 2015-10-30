package com.github.mkopylec.webbackend.jersey.mappers;

import com.github.mkopylec.webbackend.jersey.JerseyProperties;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static com.github.mkopylec.webbackend.jersey.mappers.Error.AUTHENTICATION_CREDENTIALS_NOT_FOUND_EXCEPTION_ERROR_CODE;
import static com.github.mkopylec.webbackend.jersey.mappers.Error.errorFromAuthenticationCredentialsNotFoundException;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.slf4j.LoggerFactory.getLogger;

@Provider
public class AuthenticationCredentialsNotFoundExceptionMapper extends BasicExceptionMapper<AuthenticationCredentialsNotFoundException> {

    private static final Logger log = getLogger(AuthenticationCredentialsNotFoundExceptionMapper.class);

    @Autowired
    public AuthenticationCredentialsNotFoundExceptionMapper(@Context HttpServletRequest request, JerseyProperties jersey) {
        super(request, jersey);
    }

    @Override
    public Response getResponse(AuthenticationCredentialsNotFoundException ex, String path) {
        log.warn("{} | Path: {} | Response HTTP status: {} ({}) | Message: \"{}\"",
                AUTHENTICATION_CREDENTIALS_NOT_FOUND_EXCEPTION_ERROR_CODE, path, UNAUTHORIZED.getStatusCode(), UNAUTHORIZED, ex.getMessage()
        );
        Error error = errorFromAuthenticationCredentialsNotFoundException(ex, path);
        return buildResponse(UNAUTHORIZED, error);
    }
}
