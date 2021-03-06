package com.github.mkopylec.webbackend;

import com.github.mkopylec.webbackend.jersey.EnableJerseyResources;
import com.github.mkopylec.webbackend.logging.EnableRequestTracing;
import com.github.mkopylec.webbackend.security.EnableTokenAuthorization;
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
@EnableRequestTracing
@EnableTokenAuthorization
@SpringBootApplication
public @interface WebBackendApplication {

}
