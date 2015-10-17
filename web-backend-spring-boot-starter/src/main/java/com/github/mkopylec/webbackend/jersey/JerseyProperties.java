package com.github.mkopylec.webbackend.jersey;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("web.backend.jersey")
public class JerseyProperties {

    private List<String> packages = new ArrayList<>();
    private boolean defaultExceptionMappers = true;

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
}
