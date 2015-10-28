package com.github.mkopylec.webbackend.logging.mdc;

import com.github.mkopylec.webbackend.logging.LoggingProperties;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class MdcEnabler implements Filter {

    private final LoggingProperties logging;

    public MdcEnabler(LoggingProperties logging) {
        this.logging = logging;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put(logging.getMdcKey(), randomAlphanumeric(logging.getMdcValueLength()));
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(logging.getMdcKey());
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
