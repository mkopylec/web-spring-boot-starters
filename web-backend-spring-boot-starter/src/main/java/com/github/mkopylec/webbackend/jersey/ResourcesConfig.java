package com.github.mkopylec.webbackend.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import java.util.List;

@Configuration
@ApplicationPath("")
@EnableConfigurationProperties(JerseyProperties.class)
public class ResourcesConfig extends ResourceConfig {

    @Autowired
    protected JerseyProperties jersey;

    @PostConstruct
    protected void initResources() {
        List<String> packages = jersey.getPackages();
        packages(packages.toArray(new String[packages.size()]));
    }
}
