package com.github.mkopylec.webbackend.jersey.mappers;

import com.github.mkopylec.webbackend.exceptions.ApplicationException;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;

public class Error {

    public static final String THROWABLE_ERROR_CODE = "FATAL_ERROR";
    public static final String EXCEPTION_ERROR_CODE = "UNEXPECTED_ERROR";
    public static final String WEB_APPLICATION_EXCEPTION_ERROR_CODE = "HTTP_ERROR";
    public static final String CONSTRAINT_VIOLATION_EXCEPTION_ERROR_CODE = "VALIDATION_ERROR";

    private final String errorCode;
    private final String message;
    private final String exception;
    private final String path;

    public static Error error(String errorCode, Exception ex, String path) {
        return new Error(errorCode, ex.getMessage(), ex.getClass().getName(), path);
    }

    public static Error errorFromThrowable(Throwable ex, String path) {
        return new Error(THROWABLE_ERROR_CODE, ex.getMessage(), ex.getClass().getName(), path);
    }

    public static Error errorFromException(Exception ex, String path) {
        return new Error(EXCEPTION_ERROR_CODE, ex.getMessage(), ex.getClass().getName(), path);
    }

    public static Error errorFromWebApplicationException(WebApplicationException ex, String path) {
        return new Error(WEB_APPLICATION_EXCEPTION_ERROR_CODE, ex.getMessage(), ex.getClass().getName(), path);
    }

    public static Error errorFromConstraintViolationException(ConstraintViolationException ex, String path) {
        return new Error(CONSTRAINT_VIOLATION_EXCEPTION_ERROR_CODE, ex.getMessage(), ex.getClass().getName(), path);
    }

    public static Error errorFromApplicationException(ApplicationException ex, String path) {
        return new Error(ex.getErrorCode(), ex.getMessage(), ex.getClass().getName(), path);
    }

    private Error(String errorCode, String message, String exception, String path) {
        this.errorCode = errorCode;
        this.message = message;
        this.exception = exception;
        this.path = path;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getException() {
        return exception;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("statusCode", errorCode)
                .append("message", message)
                .append("exception", exception)
                .append("path", path)
                .toString();
    }
}
