package com.github.mkopylec.webbackend.logging.mdc;

import com.github.mkopylec.webbackend.logging.LoggingProperties;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class MdcEnabler implements Filter {

    private final LoggingProperties logging;

    public MdcEnabler(LoggingProperties logging) {
        this.logging = logging;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put(logging.getMdcKey(), getMdcValue(request));
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(logging.getMdcKey());
        }
    }

    private String getMdcValue(ServletRequest request) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String mdcHeader = servletRequest.getHeader(logging.getMdcHttpHeader());
        if (isNotBlank(mdcHeader)) {
            return mdcHeader;
        }
        return randomAlphanumeric(logging.getMdcValueLength());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
