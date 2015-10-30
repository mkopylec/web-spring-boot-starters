package com.github.mkopylec.webbackend.security;

import com.github.mkopylec.webbackend.security.authorization.TokenAuthorizationFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties security;

    @Override
    public void configure(WebSecurity web) throws Exception {
        RequestMatcher requestMatcher = getIgnoredRequestMatcher();
        web.ignoring().requestMatchers(requestMatcher);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> securedPaths = security.getSecuredAntPaths();
        if (isNotEmpty(securedPaths)) {
            TokenAuthorizationFilter authenticationFilter = new TokenAuthorizationFilter(security);
            http
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(STATELESS)
                    .and()
                    .addFilterBefore(authenticationFilter, FilterSecurityInterceptor.class);
        }
    }

    private RequestMatcher getIgnoredRequestMatcher() {
        List<String> securedPaths = security.getSecuredAntPaths();
        securedPaths.removeIf(StringUtils::isBlank);

        if (isNotEmpty(securedPaths)) {
            List<RequestMatcher> requestMatchers = securedPaths.stream()
                    .map(AntPathRequestMatcher::new)
                    .collect(toList());
            return new NegatedRequestMatcher(new OrRequestMatcher(requestMatchers));
        } else {
            return new AntPathRequestMatcher("/**");
        }
    }
}
