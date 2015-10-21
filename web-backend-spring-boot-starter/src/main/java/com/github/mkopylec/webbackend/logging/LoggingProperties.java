package com.github.mkopylec.webbackend.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("web.backend.logging")
public class LoggingProperties {

    private String mdcKey = "requestId";
    private int mdcValueLength = 10;

    public String getMdcKey() {
        return mdcKey;
    }

    public void setMdcKey(String mdcKey) {
        this.mdcKey = mdcKey;
    }

    public int getMdcValueLength() {
        return mdcValueLength;
    }

    public void setMdcValueLength(int mdcValueLength) {
        this.mdcValueLength = mdcValueLength;
    }
}
