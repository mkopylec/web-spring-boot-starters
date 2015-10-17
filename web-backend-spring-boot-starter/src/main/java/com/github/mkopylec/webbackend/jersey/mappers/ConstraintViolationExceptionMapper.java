package com.github.mkopylec.webbackend.jersey.mappers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.slf4j.LoggerFactory.getLogger;

@Provider
public class ConstraintViolationExceptionMapper extends BasicExceptionMapper<ConstraintViolationException> {

    private static final Logger log = getLogger(ConstraintViolationExceptionMapper.class);

    @Autowired
    protected ConstraintViolationExceptionMapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response getResponse(ConstraintViolationException ex, String path) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        List<Error> errors = new ArrayList<>(violations.size());
        if (violations.isEmpty()) {
            log.warn(String.format("%s | Path: %s | Response HTTP status: %d (%s)",
                            Error.CONSTRAINT_VIOLATION_EXCEPTION_ERROR_CODE, path, BAD_REQUEST.getStatusCode(), BAD_REQUEST), ex
            );
            Error error = Error.errorFromConstraintViolationException(ex, path);
            errors.add(error);
        } else {
            violations.forEach(violation -> {
                log.warn("{} | Path: {} | Response HTTP status: {} ({}) | Violated element: {}='{}'",
                        violation.getMessage(), path, BAD_REQUEST.getStatusCode(), BAD_REQUEST, violation.getPropertyPath(), violation.getInvalidValue()
                );
                Error error = Error.error(violation.getMessage(), ex, path);
                errors.add(error);
            });
        }
        return buildResponse(BAD_REQUEST, errors);
    }
}
