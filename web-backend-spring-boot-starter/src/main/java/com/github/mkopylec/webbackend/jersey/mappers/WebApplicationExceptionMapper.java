package com.github.mkopylec.webbackend.jersey.mappers;

import com.github.mkopylec.webbackend.jersey.JerseyProperties;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static com.github.mkopylec.webbackend.jersey.mappers.Error.WEB_APPLICATION_EXCEPTION_ERROR_CODE;
import static com.github.mkopylec.webbackend.jersey.mappers.Error.errorFromWebApplicationException;
import static org.slf4j.LoggerFactory.getLogger;

@Provider
public class WebApplicationExceptionMapper extends BasicExceptionMapper<WebApplicationException> {

    private static final Logger log = getLogger(WebApplicationExceptionMapper.class);

    @Autowired
    public WebApplicationExceptionMapper(HttpServletRequest request, JerseyProperties jersey) {
        super(request, jersey);
    }

    @Override
    public Response getResponse(WebApplicationException ex, String path) {
        Response response = ex.getResponse();
        log.warn("{} | Path: {} | Response HTTP status: {} ({})",
                WEB_APPLICATION_EXCEPTION_ERROR_CODE, path, response.getStatus(), response.getStatusInfo()
        );
        Error error = errorFromWebApplicationException(ex, path);
        return buildResponse(response.getStatusInfo(), error);
    }
}
