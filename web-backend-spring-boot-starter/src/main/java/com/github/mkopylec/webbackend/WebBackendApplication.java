package com.github.mkopylec.webbackend;

import com.github.mkopylec.webbackend.jersey.EnableJerseyResources;
import com.github.mkopylec.webbackend.logging.EnableMdcLogger;
import com.github.mkopylec.webbackend.security.EnableTokenAuthorization;
import com.github.mkopylec.webbackend.security.SecurityDisabledConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Import(SecurityDisabledConfiguration.class)
@EnableJerseyResources
@EnableMdcLogger
@EnableTokenAuthorization
@SpringBootApplication
public @interface WebBackendApplication {

}
