package com.github.mkopylec.webbackend.security.ignore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredEndpointsConfiguration {

    @Autowired
    private ApplicationContext context;

    @Bean
    @ConditionalOnMissingBean(name = "jerseyConfiguration")
    public SecuredEndpointsScanner springEndpointsScanner() {
        return new SpringSecuredEndpointsScanner(context);
    }

    @Bean
    @ConditionalOnBean(name = "jerseyConfiguration")
    public SecuredEndpointsScanner jerseyEndpointsScanner() {
        return new JerseySecuredEndpointsScanner(context);
    }
}
