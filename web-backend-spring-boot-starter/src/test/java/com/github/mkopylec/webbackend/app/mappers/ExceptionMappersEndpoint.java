package com.github.mkopylec.webbackend.app.mappers;

import com.github.mkopylec.webbackend.exceptions.ApplicationException;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static com.github.mkopylec.webbackend.app.Strings.APPLICATION_EXCEPTION_CODE;
import static com.github.mkopylec.webbackend.app.Strings.APPLICATION_EXCEPTION_MESSAGE;
import static com.github.mkopylec.webbackend.app.Strings.CUSTOM_EXCEPTION_MESSAGE;
import static com.github.mkopylec.webbackend.app.Strings.EXCEPTION_MESSAGE;
import static com.github.mkopylec.webbackend.app.Strings.THROWABLE_MESSAGE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_IMPLEMENTED;

@Component
@Path("mappers")
@Produces(APPLICATION_JSON)
public class ExceptionMappersEndpoint {

    @GET
    @Path("throwable")
    public void failWithThrowable() throws Throwable {
        throw new Throwable(THROWABLE_MESSAGE);
    }

    @GET
    @Path("exception")
    public void failWithException() throws Exception {
        throw new Exception(EXCEPTION_MESSAGE);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Path("constraint")
    public void failWithConstraintViolationException(@Valid RequestData data) {
    }

    @GET
    @Path("application")
    public void failWithApplicationException() {
        throw new ApplicationException(APPLICATION_EXCEPTION_CODE, APPLICATION_EXCEPTION_MESSAGE, NOT_IMPLEMENTED) {

        };
    }

    @GET
    @Path("custom")
    public void failWithCustomException() {
        throw new CustomException(CUSTOM_EXCEPTION_MESSAGE);
    }
}
