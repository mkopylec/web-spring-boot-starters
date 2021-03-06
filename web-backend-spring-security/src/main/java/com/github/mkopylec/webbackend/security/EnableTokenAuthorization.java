package com.github.mkopylec.webbackend.security;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Import({SecurityConfiguration.class, MethodSecurityConfiguration.class})
public @interface EnableTokenAuthorization {

}
