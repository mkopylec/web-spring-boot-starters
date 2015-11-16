package com.github.mkopylec.webbackend.jersey;

import com.github.mkopylec.webbackend.jersey.mappers.BasicExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import java.util.ArrayList;
import java.util.List;

@Configuration("jerseyConfiguration")
@ApplicationPath("")
@EnableConfigurationProperties(JerseyProperties.class)
public class JerseyConfiguration extends ResourceConfig {

    @Autowired
    protected JerseyProperties jersey;

    @PostConstruct
    protected void initResources() {
        List<String> packages = new ArrayList<>(jersey.getPackages());
        if (jersey.isDefaultExceptionMappers()) {
            packages.add(BasicExceptionMapper.class.getPackage().getName());
        }
        packages(packages.toArray(new String[packages.size()]));
    }
}
