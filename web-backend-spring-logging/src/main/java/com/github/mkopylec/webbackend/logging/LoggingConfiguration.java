package com.github.mkopylec.webbackend.logging;

import com.github.mkopylec.webbackend.logging.mdc.MdcEnabler;
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
        MdcEnabler mdcEnabler = new MdcEnabler(logging);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(mdcEnabler);
        registrationBean.setOrder(HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
