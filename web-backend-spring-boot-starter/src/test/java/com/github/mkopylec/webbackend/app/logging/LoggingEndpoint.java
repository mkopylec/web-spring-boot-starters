package com.github.mkopylec.webbackend.app.logging;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import static com.github.mkopylec.webbackend.app.Constants.LOGGER_MESSAGE;
import static org.slf4j.LoggerFactory.getLogger;

@Component
@Path("logging")
public class LoggingEndpoint {

    private static final Logger log = getLogger(LoggingEndpoint.class);

    @GET
    public void logMessage() {
        log.info(LOGGER_MESSAGE);
    }
}
