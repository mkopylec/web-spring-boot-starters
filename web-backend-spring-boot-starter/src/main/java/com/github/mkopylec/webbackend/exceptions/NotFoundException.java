package com.github.mkopylec.webbackend.exceptions;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class NotFoundException extends ApplicationException {

    public NotFoundException(String errorCode, String message) {
        super(errorCode, message, NOT_FOUND);
    }
}
