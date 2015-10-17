package com.github.mkopylec.webbackend.jersey.mappers;

import com.github.mkopylec.webbackend.exceptions.ApplicationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static org.slf4j.LoggerFactory.getLogger;

@Provider
public class ApplicationExceptionMapper extends BasicExceptionMapper<ApplicationException> {

    private static final Logger log = getLogger(ApplicationExceptionMapper.class);

    @Autowired
    public ApplicationExceptionMapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response getResponse(ApplicationException ex, String path) {
        Response response = ex.getResponse();
        log.warn("{}. Path: {}. Response HTTP status: {} ({}). Message: \"{}\"",
                ex.getErrorCode(), path, response.getStatus(), response.getStatusInfo(), ex.getMessage()
        );
        Error error = Error.errorFromApplicationException(ex, path);
        return buildResponse(response.getStatusInfo(), error);
    }
}
