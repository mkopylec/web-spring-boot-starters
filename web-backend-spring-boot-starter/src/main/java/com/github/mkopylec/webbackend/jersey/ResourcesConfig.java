package com.github.mkopylec.webbackend.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("")
public class ResourcesConfig extends ResourceConfig {

    public ResourcesConfig() {
        //TODO Add endpoints and providers using Allegro live templates
    }
}
