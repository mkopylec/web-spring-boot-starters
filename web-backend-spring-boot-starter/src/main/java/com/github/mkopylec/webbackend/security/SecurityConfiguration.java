package com.github.mkopylec.webbackend.security;

import com.github.mkopylec.webbackend.security.authorization.TokenAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties security;

    @Override
    public void configure(WebSecurity web) throws Exception {
        List<String> ignoredPaths = security.getIgnoredPaths();
        web.ignoring().antMatchers(ignoredPaths.toArray(new String[ignoredPaths.size()]));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        TokenAuthorizationFilter authenticationFilter = new TokenAuthorizationFilter(security);
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .addFilterBefore(authenticationFilter, FilterSecurityInterceptor.class);
    }
}
