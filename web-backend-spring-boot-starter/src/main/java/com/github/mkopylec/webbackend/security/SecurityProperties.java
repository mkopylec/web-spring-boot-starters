package com.github.mkopylec.webbackend.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("web.backend.security")
public class SecurityProperties {

    private String tokenKeyFile = "token_key.pem";
    private List<String> ignoredPaths = new ArrayList<>();

    public String getTokenKeyFile() {
        return tokenKeyFile;
    }

    public void setTokenKeyFile(String tokenKeyFile) {
        this.tokenKeyFile = tokenKeyFile;
    }

    public List<String> getIgnoredPaths() {
        return ignoredPaths;
    }

    public void setIgnoredPaths(List<String> ignoredPaths) {
        this.ignoredPaths = ignoredPaths;
    }
}
