package com.github.mkopylec.webbackend.app.mappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

import static com.github.mkopylec.webbackend.app.Strings.EMPTY_CONTENT;
import static com.github.mkopylec.webbackend.app.Strings.INVALID_NUMBER;

public class RequestData {

    @NotBlank(message = EMPTY_CONTENT)
    private final String content;

    @Min(message = INVALID_NUMBER, value = 1)
    private final int number;

    @JsonCreator
    public RequestData(
            @JsonProperty("content") String content,
            @JsonProperty("number") int number
    ) {
        this.content = content;
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public int getNumber() {
        return number;
    }
}
