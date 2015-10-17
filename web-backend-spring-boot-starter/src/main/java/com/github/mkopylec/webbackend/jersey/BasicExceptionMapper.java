package com.github.mkopylec.webbackend.jersey;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.List;

import static java.util.Collections.singletonList;
import static javax.ws.rs.core.Response.status;

public abstract class BasicExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {

    private final HttpServletRequest request;

    protected BasicExceptionMapper(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Response toResponse(E ex) {
        return getResponse(ex, request.getRequestURI());
    }

    abstract Response getResponse(E ex, String path);

    protected Response buildResponse(StatusType httpStatus, Error error) {
        return buildResponse(httpStatus, singletonList(error));
    }

    protected Response buildResponse(StatusType httpStatus, List<Error> errors) {
        return status(httpStatus)
                .entity(errors)
                .build();
    }
}
