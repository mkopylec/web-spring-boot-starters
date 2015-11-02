package com.github.mkopylec.webbackend.jersey.exceptions;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

public class ServerException extends ApplicationException {

    public ServerException(String errorCode, String message) {
        super(errorCode, message, INTERNAL_SERVER_ERROR);
    }
}
