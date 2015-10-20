package com.github.mkopylec.webbackend.jersey.mappers;

import com.github.mkopylec.webbackend.jersey.JerseyProperties;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static com.github.mkopylec.webbackend.jersey.mappers.Error.ACCESS_DENIED_EXCEPTION_ERROR_CODE;
import static com.github.mkopylec.webbackend.jersey.mappers.Error.errorFromAccessDeniedException;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.slf4j.LoggerFactory.getLogger;

@Provider
public class AccessDeniedExceptionMapper extends BasicExceptionMapper<AccessDeniedException> {

    private static final Logger log = getLogger(AccessDeniedExceptionMapper.class);

    @Autowired
    public AccessDeniedExceptionMapper(@Context HttpServletRequest request, JerseyProperties jersey) {
        super(request, jersey);
    }

    @Override
    public Response getResponse(AccessDeniedException ex, String path) {
        log.warn("{} | Path: {} | Response HTTP status: {} ({}) | Message: \"{}\"",
                ACCESS_DENIED_EXCEPTION_ERROR_CODE, path, UNAUTHORIZED.getStatusCode(), UNAUTHORIZED, ex.getMessage()
        );
        Error error = errorFromAccessDeniedException(ex, path);
        return buildResponse(UNAUTHORIZED, error);
    }
}
