package com.github.mkopylec.webbackend.jersey.mappers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static com.github.mkopylec.webbackend.jersey.mappers.Error.EXCEPTION_ERROR_CODE;
import static com.github.mkopylec.webbackend.jersey.mappers.Error.errorFromException;
import static java.lang.String.format;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.slf4j.LoggerFactory.getLogger;

@Provider
public class ExceptionMapper extends BasicExceptionMapper<Exception> {

    private static final Logger log = getLogger(ExceptionMapper.class);

    @Autowired
    public ExceptionMapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response getResponse(Exception ex, String path) {
        log.error(format("%s | Path: %s | Response HTTP status: %d (%s)",
                        EXCEPTION_ERROR_CODE, path, INTERNAL_SERVER_ERROR.getStatusCode(), INTERNAL_SERVER_ERROR), ex
        );
        Error error = errorFromException(ex, path);
        return buildResponse(INTERNAL_SERVER_ERROR, error);
    }
}
