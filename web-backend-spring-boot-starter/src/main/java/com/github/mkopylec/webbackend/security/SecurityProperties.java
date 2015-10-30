package com.github.mkopylec.webbackend.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import static java.util.Collections.singletonList;

@ConfigurationProperties("web.backend.security")
public class SecurityProperties {

    private String tokenKeyFile = "token_key.pem";
    private List<String> ignoredAntPaths = singletonList("/**");

    public String getTokenKeyFile() {
        return tokenKeyFile;
    }

    public void setTokenKeyFile(String tokenKeyFile) {
        this.tokenKeyFile = tokenKeyFile;
    }

    public List<String> getIgnoredAntPaths() {
        return ignoredAntPaths;
    }

    public void setIgnoredAntPaths(List<String> ignoredAntPaths) {
        this.ignoredAntPaths = ignoredAntPaths;
    }
}
