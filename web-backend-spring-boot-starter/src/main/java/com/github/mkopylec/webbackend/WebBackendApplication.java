package com.github.mkopylec.webbackend;

import com.github.mkopylec.webbackend.jersey.EnableJerseyResources;
import com.github.mkopylec.webbackend.jersey.ResourcesConfig;
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
@Import(ResourcesConfig.class)
@EnableJerseyResources
@SpringBootApplication
public @interface WebBackendApplication {

}
