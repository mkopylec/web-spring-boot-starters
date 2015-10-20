package com.github.mkopylec.webbackend;

import com.github.mkopylec.webbackend.authentication.EnableTokenAuthorization;
import com.github.mkopylec.webbackend.jersey.EnableJerseyResources;
import com.github.mkopylec.webbackend.logging.EnableMdcLogger;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
@EnableJerseyResources
@EnableMdcLogger
@EnableTokenAuthorization
@SpringBootApplication
public @interface WebBackendApplication {

}
