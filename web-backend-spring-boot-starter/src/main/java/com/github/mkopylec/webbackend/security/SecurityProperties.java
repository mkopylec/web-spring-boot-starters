package com.github.mkopylec.webbackend.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("web.backend.security")
public class SecurityProperties {

    private String tokenKeyFile = "token_key.pem";

    public String getTokenKeyFile() {
        return tokenKeyFile;
    }

    public void setTokenKeyFile(String tokenKeyFile) {
        this.tokenKeyFile = tokenKeyFile;
    }
}
