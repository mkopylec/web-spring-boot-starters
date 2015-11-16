package com.github.mkopylec.webbackend.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("web.backend.logging")
public class LoggingProperties {

    private int requestIdLength = 10;
    private String requestIdHeader = "X-Request-Id";

    public int getRequestIdLength() {
        return requestIdLength;
    }

    public void setRequestIdLength(int requestIdLength) {
        this.requestIdLength = requestIdLength;
    }

    public String getRequestIdHeader() {
        return requestIdHeader;
    }

    public void setRequestIdHeader(String requestIdHeader) {
        this.requestIdHeader = requestIdHeader;
    }
}
