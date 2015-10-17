package com.github.mkopylec.webbackend.jersey.mappers;

import com.github.mkopylec.webbackend.jersey.JerseyProperties;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.List;

import static java.util.Collections.singletonList;
import static javax.ws.rs.core.Response.status;

public abstract class BasicExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {

    private final HttpServletRequest request;
    private final String mediaType;

    protected BasicExceptionMapper(HttpServletRequest request, JerseyProperties jersey) {
        this.request = request;
        this.mediaType = jersey.getErrorMediaType();
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
                .type(mediaType)
                .entity(errors)
                .build();
    }
}
