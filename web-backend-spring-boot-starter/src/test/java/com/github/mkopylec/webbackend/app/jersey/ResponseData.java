package com.github.mkopylec.webbackend.app.jersey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseData {

    private final int number;

    @JsonCreator
    public ResponseData(
            @JsonProperty("number") int number
    ) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
