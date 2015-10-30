package com.github.mkopylec.webbackend.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("web.backend.security")
public class SecurityProperties {

    private String tokenKeyFile = "token_key.pem";
    private List<String> securedAntPaths = new ArrayList<>();

    public String getTokenKeyFile() {
        return tokenKeyFile;
    }

    public void setTokenKeyFile(String tokenKeyFile) {
        this.tokenKeyFile = tokenKeyFile;
    }

    public List<String> getSecuredAntPaths() {
        return securedAntPaths;
    }

    public void setSecuredAntPaths(List<String> securedAntPaths) {
        this.securedAntPaths = securedAntPaths;
    }
}
