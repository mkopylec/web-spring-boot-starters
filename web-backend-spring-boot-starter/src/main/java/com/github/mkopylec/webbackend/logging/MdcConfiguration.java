package com.github.mkopylec.webbackend.logging;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
@EnableConfigurationProperties(MdcLoggingProperties.class)
public class MdcConfiguration {

    @Bean
    public FilterRegistrationBean mdcFilter(MdcLoggingProperties mdcLogging) {
        MdcEnabler mdcEnabler = new MdcEnabler(mdcLogging);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(mdcEnabler);
        registrationBean.setOrder(HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
