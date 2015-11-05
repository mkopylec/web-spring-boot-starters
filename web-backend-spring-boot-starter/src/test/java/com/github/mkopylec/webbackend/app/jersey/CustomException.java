package com.github.mkopylec.webbackend.app.jersey;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
