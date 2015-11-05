package com.github.mkopylec.webbackend.jersey.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public abstract class ApplicationException extends WebApplicationException {

    private final String errorCode;

    public ApplicationException(String errorCode, String message, Status httpStatus) {
        super(message, httpStatus);
        checkArgument(isNotBlank(errorCode), "Error code cannot be empty");
        checkArgument(isNotBlank(message), "Error message cannot be empty");
        checkArgument(httpStatus != null, "Error HTTP status cannot be empty");
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
