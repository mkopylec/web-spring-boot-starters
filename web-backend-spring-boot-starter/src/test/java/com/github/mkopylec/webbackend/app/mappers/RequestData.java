package com.github.mkopylec.webbackend.app.mappers;

import org.hibernate.validator.constraints.NotBlank;

import static com.github.mkopylec.webbackend.app.Strings.EMPTY_CONTENT;

public class RequestData {

    @NotBlank(message = EMPTY_CONTENT)
    private final String content;

    public RequestData(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
