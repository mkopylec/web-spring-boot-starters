package com.github.mkopylec.webbackend.logging;

import com.github.mkopylec.webbackend.logging.mdc.RequestTraceEnabler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingConfiguration {

    @Autowired
    private LoggingProperties logging;

    @Bean
    public FilterRegistrationBean mdcFilter() {
        RequestTraceEnabler traceEnabler = new RequestTraceEnabler(logging);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(traceEnabler);
        registrationBean.setOrder(HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
