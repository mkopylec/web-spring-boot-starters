package com.github.mkopylec.webbackend.jersey.mappers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static com.github.mkopylec.webbackend.jersey.mappers.Error.THROWABLE_ERROR_CODE;
import static com.github.mkopylec.webbackend.jersey.mappers.Error.errorFromThrowable;
import static java.lang.String.format;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.slf4j.LoggerFactory.getLogger;

@Provider
public class ThrowableMapper extends BasicExceptionMapper<Throwable> {

    private static final Logger log = getLogger(ThrowableMapper.class);

    @Autowired
    public ThrowableMapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response getResponse(Throwable ex, String path) {
        log.error(format("%s. Path: %s. Response HTTP status: %d (%s)",
                        THROWABLE_ERROR_CODE, path, INTERNAL_SERVER_ERROR.getStatusCode(), INTERNAL_SERVER_ERROR), ex
        );
        Error error = errorFromThrowable(ex, path);
        return buildResponse(INTERNAL_SERVER_ERROR, error);
    }
}
