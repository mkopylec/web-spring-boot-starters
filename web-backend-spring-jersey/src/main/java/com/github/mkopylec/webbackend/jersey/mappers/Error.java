package com.github.mkopylec.webbackend.jersey.mappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mkopylec.webbackend.jersey.exceptions.ApplicationException;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;

public class Error {

    public static final String THROWABLE_ERROR_CODE = "FATAL_ERROR";
    public static final String EXCEPTION_ERROR_CODE = "UNEXPECTED_ERROR";
    public static final String WEB_APPLICATION_EXCEPTION_ERROR_CODE = "HTTP_ERROR";
    public static final String CONSTRAINT_VIOLATION_EXCEPTION_ERROR_CODE = "VALIDATION_ERROR";
    public static final String ACCESS_DENIED_EXCEPTION_ERROR_CODE = "SECURITY_ERROR";

    public static final String ERROR_CODE_FIELD = "errorCode";
    public static final String MESSAGE_FIELD = "message";
    public static final String EXCEPTION_FIELD = "exception";
    public static final String PATH_FIELD = "path";

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

    public static Error errorFromAccessDeniedException(Exception ex, String path) {
        return new Error(ACCESS_DENIED_EXCEPTION_ERROR_CODE, ex.getMessage(), ex.getClass().getName(), path);
    }

    public static Error errorFromApplicationException(ApplicationException ex, String path) {
        return new Error(ex.getErrorCode(), ex.getMessage(), ex.getClass().getName(), path);
    }

    @JsonCreator
    public Error(
            @JsonProperty(ERROR_CODE_FIELD) String errorCode,
            @JsonProperty(MESSAGE_FIELD) String message,
            @JsonProperty(EXCEPTION_FIELD) String exception,
            @JsonProperty(PATH_FIELD) String path
    ) {
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
                .append(ERROR_CODE_FIELD, errorCode)
                .append(MESSAGE_FIELD, message)
                .append(EXCEPTION_FIELD, exception)
                .append(PATH_FIELD, path)
                .toString();
    }
}
