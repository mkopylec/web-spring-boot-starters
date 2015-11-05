package com.github.mkopylec.webbackend.jersey.mappers;

import com.github.mkopylec.webbackend.jersey.JerseyProperties;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static com.github.mkopylec.webbackend.jersey.mappers.Error.ACCESS_DENIED_EXCEPTION_ERROR_CODE;
import static com.github.mkopylec.webbackend.jersey.mappers.Error.EXCEPTION_ERROR_CODE;
import static com.github.mkopylec.webbackend.jersey.mappers.Error.errorFromAccessDeniedException;
import static com.github.mkopylec.webbackend.jersey.mappers.Error.errorFromException;
import static java.lang.String.format;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.slf4j.LoggerFactory.getLogger;

@Provider
public class ExceptionMapper extends BasicExceptionMapper<Exception> {

    private static final String ACCESS_DENIED_EXCEPTION_CLASS = "org.springframework.security.access.AccessDeniedException";

    private static final Logger log = getLogger(ExceptionMapper.class);

    @Autowired
    public ExceptionMapper(@Context HttpServletRequest request, JerseyProperties jersey) {
        super(request, jersey);
    }

    @Override
    public Response getResponse(Exception ex, String path) {
        if (ex.getClass().getName().equals(ACCESS_DENIED_EXCEPTION_CLASS)) {
            return mapAccessDeniedException(ex, path);
        }
        return mapException(ex, path);
    }

    private Response mapAccessDeniedException(Exception ex, String path) {
        log.warn("{} | Path: {} | Response HTTP status: {} ({}) | Message: \"{}\"",
                ACCESS_DENIED_EXCEPTION_ERROR_CODE, path, UNAUTHORIZED.getStatusCode(), UNAUTHORIZED, ex.getMessage()
        );
        Error error = errorFromAccessDeniedException(ex, path);
        return buildResponse(UNAUTHORIZED, error);
    }

    private Response mapException(Exception ex, String path) {
        log.error(format("%s | Path: %s | Response HTTP status: %d (%s)",
                        EXCEPTION_ERROR_CODE, path, INTERNAL_SERVER_ERROR.getStatusCode(), INTERNAL_SERVER_ERROR), ex
        );
        Error error = errorFromException(ex, path);
        return buildResponse(INTERNAL_SERVER_ERROR, error);
    }
}
