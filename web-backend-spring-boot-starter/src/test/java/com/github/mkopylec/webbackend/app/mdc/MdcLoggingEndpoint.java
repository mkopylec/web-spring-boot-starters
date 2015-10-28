package com.github.mkopylec.webbackend.app.mdc;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import static com.github.mkopylec.webbackend.app.Strings.LOGGER_MESSAGE;
import static org.slf4j.LoggerFactory.getLogger;

@Component
@Path("logging")
public class MdcLoggingEndpoint {

    private static final Logger log = getLogger(MdcLoggingEndpoint.class);

    @GET
    public void logMessage() {
        log.info(LOGGER_MESSAGE);
    }
}
