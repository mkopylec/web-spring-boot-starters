package com.github.mkopylec.webbackend.app.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static com.github.mkopylec.webbackend.app.Constants.RESPONSE_DATA_MESSAGE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("security")
@Produces(APPLICATION_JSON)
public class SecurityEndpoint {

    @GET
    @Path("authenticated")
    @PreAuthorize("authentication.authenticated")
    public ResponseData permitAuthenticated() {
        return new ResponseData(RESPONSE_DATA_MESSAGE);
    }

    @GET
    @Path("authority")
    @PreAuthorize("hasRole('USER')")
    public ResponseData permitUser() {
        return new ResponseData(RESPONSE_DATA_MESSAGE);
    }
}
