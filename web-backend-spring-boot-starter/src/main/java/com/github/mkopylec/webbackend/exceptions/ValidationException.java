package com.github.mkopylec.webbackend.exceptions;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class ValidationException extends ApplicationException {

    public ValidationException(String errorCode, String message) {
        super(errorCode, message, BAD_REQUEST);
    }
}
