package com.github.mkopylec.webbackend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.springframework.security.jwt.JwtHelper.decodeAndVerify;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String CLASSPATH_PREFIX = "classpath:";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer";
    private static final String SUBJECT_CLAIM = "sub";
    private static final String EXPIRATION_CLAIM = "exp";
    private static final long DEFAULT_EXPIRATION = 1l;

    private final ObjectMapper mapper = new ObjectMapper();
    private SignatureVerifier verifier;

    public TokenAuthenticationFilter(SecurityProperties security) throws IOException {
        super(new AntPathRequestMatcher("/**"));
        setAuthenticationSuccessHandler(new EmptyAuthenticationHandler());
        setContinueChainBeforeSuccessfulAuthentication(true);
        setVerifier(security);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        //TODO real authorization
        AuthorizationToken authorizationToken = new AuthorizationToken();
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (isNotBlank(authorization)) {
            String token = removeStart(authorization, BEARER_PREFIX).trim();
            Jwt jwt = decodeAndVerify(token, verifier);
            JwtToken jwtToken = mapper.readValue(jwt.getClaims(), JwtToken.class);
            authorizationToken.setPrincipal(jwtToken.getSubject());
            authorizationToken.setAuthorities(jwtToken.getAuthorities());
            if (jwtToken.isNotExpired()) {
                authorizationToken.setAuthenticated(true);
            }
        }
        SecurityContextHolder.getContext().setAuthentication(authorizationToken);
        return authorizationToken;
    }

    private void setVerifier(SecurityProperties security) throws IOException {
        String tokenKey = security.getTokenKey();
        if (startsWith(tokenKey, CLASSPATH_PREFIX)) {
            InputStream stream = new ClassPathResource(tokenKey).getInputStream();
            tokenKey = IOUtils.toString(stream);
        }
        verifier = new RsaVerifier(tokenKey);
    }
}
