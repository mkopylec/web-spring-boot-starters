package com.github.mkopylec.webbackend.security.authorization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import static java.lang.System.currentTimeMillis;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonWebToken {

    public static final String SUBJECT_CLAIM = "sub";
    public static final String EXPIRATION_TIME_CLAIM = "exp";
    public static final String AUTHORITIES_CLAIM = "auth";

    @JsonProperty(SUBJECT_CLAIM)
    private final String subject;
    @JsonProperty(EXPIRATION_TIME_CLAIM)
    private final long expirationTime;
    @JsonProperty(AUTHORITIES_CLAIM)
    private final List<String> authorities;

    @JsonCreator
    public JsonWebToken(
            @JsonProperty(SUBJECT_CLAIM) String subject,
            @JsonProperty(EXPIRATION_TIME_CLAIM) long expirationTime,
            @JsonProperty(AUTHORITIES_CLAIM) List<String> authorities
    ) {
        this.subject = subject;
        this.expirationTime = expirationTime;
        this.authorities = authorities;
    }

    @JsonIgnore
    public String getSubject() {
        return subject;
    }

    @JsonIgnore
    public boolean isExpired() {
        return expirationTime < currentTimeMillis() / 1000;
    }

    @JsonIgnore
    public List<GrantedAuthority> getAuthorities() {
        return createAuthorityList(authorities.toArray(new String[authorities.size()]));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(SUBJECT_CLAIM, subject)
                .append(EXPIRATION_TIME_CLAIM, expirationTime)
                .append(AUTHORITIES_CLAIM, authorities)
                .toString();
    }
}
