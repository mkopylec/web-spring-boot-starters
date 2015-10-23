package com.github.mkopylec.webbackend.security.authorization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import static java.lang.System.currentTimeMillis;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtToken {

    private final String subject;
    private final long expirationTime;
    private final List<String> authorities;

    @JsonCreator
    public JwtToken(
            @JsonProperty("sub") String subject,
            @JsonProperty("exp") Long expirationTime,
            @JsonProperty("auth") List<String> authorities
    ) {
        this.subject = subject;
        this.expirationTime = expirationTime;
        this.authorities = authorities;
    }

    public String getSubject() {
        return subject;
    }

    public boolean isNotExpired() {
        return expirationTime > currentTimeMillis();
    }

    public List<GrantedAuthority> getAuthorities() {
        return createAuthorityList(authorities.toArray(new String[authorities.size()]));
    }
}
