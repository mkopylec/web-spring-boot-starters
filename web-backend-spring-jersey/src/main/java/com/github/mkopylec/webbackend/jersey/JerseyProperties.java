package com.github.mkopylec.webbackend.jersey;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@ConfigurationProperties("web.backend.jersey")
public class JerseyProperties {

    private List<String> packages = new ArrayList<>();
    private boolean defaultExceptionMappers = true;
    private String errorMediaType = APPLICATION_JSON;

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    public boolean isDefaultExceptionMappers() {
        return defaultExceptionMappers;
    }

    public void setDefaultExceptionMappers(boolean defaultExceptionMappers) {
        this.defaultExceptionMappers = defaultExceptionMappers;
    }

    public String getErrorMediaType() {
        return errorMediaType;
    }

    public void setErrorMediaType(String errorMediaType) {
        this.errorMediaType = errorMediaType;
    }
}
