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

public class RequestTraceEnabler implements Filter {

    public static final String REQUEST_ID_KEY = "requestId";

    private final LoggingProperties logging;

    public RequestTraceEnabler(LoggingProperties logging) {
        this.logging = logging;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put(REQUEST_ID_KEY, getMdcValue(request));
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(REQUEST_ID_KEY);
        }
    }

    private String getMdcValue(ServletRequest request) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String mdcHeader = servletRequest.getHeader(logging.getRequestIdHeader());
        if (isNotBlank(mdcHeader)) {
            return mdcHeader;
        }
        return randomAlphanumeric(logging.getRequestIdLength());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
