package com.github.mkopylec.webbackend.jersey.mappers;

import com.github.mkopylec.webbackend.jersey.exceptions.ApplicationException;
import com.github.mkopylec.webbackend.jersey.JerseyProperties;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static com.github.mkopylec.webbackend.jersey.mappers.Error.errorFromApplicationException;
import static org.slf4j.LoggerFactory.getLogger;

@Provider
public class ApplicationExceptionMapper extends BasicExceptionMapper<ApplicationException> {

    private static final Logger log = getLogger(ApplicationExceptionMapper.class);

    @Autowired
    public ApplicationExceptionMapper(@Context HttpServletRequest request, JerseyProperties jersey) {
        super(request, jersey);
    }

    @Override
    public Response getResponse(ApplicationException ex, String path) {
        Response response = ex.getResponse();
        log.warn("{} | Path: {} | Response HTTP status: {} ({}) | Message: \"{}\"",
                ex.getErrorCode(), path, response.getStatus(), response.getStatusInfo(), ex.getMessage()
        );
        Error error = errorFromApplicationException(ex, path);
        return buildResponse(response.getStatusInfo(), error);
    }
}
