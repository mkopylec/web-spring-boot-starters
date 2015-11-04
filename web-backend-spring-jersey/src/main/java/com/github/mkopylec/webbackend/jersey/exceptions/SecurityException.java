package com.github.mkopylec.webbackend.jersey.exceptions;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

public class SecurityException extends ApplicationException {

    public SecurityException(String errorCode, String message) {
        super(errorCode, message, UNAUTHORIZED);
    }
}
