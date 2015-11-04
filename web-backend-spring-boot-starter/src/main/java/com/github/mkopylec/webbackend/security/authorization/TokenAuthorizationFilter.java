package com.github.mkopylec.webbackend.security.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mkopylec.webbackend.security.SecurityProperties;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
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

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.security.jwt.JwtHelper.decodeAndVerify;

public class TokenAuthorizationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer";

    private static final Logger log = getLogger(TokenAuthorizationFilter.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private SignatureVerifier verifier;

    public TokenAuthorizationFilter(SecurityProperties security) throws IOException {
        super(new AntPathRequestMatcher("/**"));
        setAuthenticationSuccessHandler(new EmptyAuthenticationHandler());
        setContinueChainBeforeSuccessfulAuthentication(true);
        setVerifier(security);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        AuthorizationToken authorizationToken = new AuthorizationToken();
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        try {
            if (isNotBlank(authorization)) {
                JsonWebToken jwt = decodeToken(authorization);
                if (jwt != null) {
                    log.info("Authorizing token: {}", jwt);
                    authorizationToken.setPrincipal(jwt.getSubject());
                    authorizationToken.setAuthorities(jwt.getAuthorities());
                    if (jwt.isNotExpired()) {
                        authorizationToken.setAuthenticated(true);
                    }
                }
            }
        } catch (Exception ex) {
            log.error(format("Error authorizing token. Authorization header: '%s'", authorization), ex);
        }
        SecurityContextHolder.getContext().setAuthentication(authorizationToken);

        return authorizationToken;
    }

    private void setVerifier(SecurityProperties security) throws IOException {
        String tokenKey = security.getTokenKeyFile();
        InputStream stream = new ClassPathResource(tokenKey).getInputStream();
        tokenKey = IOUtils.toString(stream);
        verifier = new RsaVerifier(tokenKey);
    }

    private JsonWebToken decodeToken(String authorization) {
        String token = removeStart(authorization, BEARER_PREFIX).trim();
        try {
            Jwt jwt = decodeAndVerify(token, verifier);
            return mapper.readValue(jwt.getClaims(), JsonWebToken.class);
        } catch (Exception ex) {
            log.warn("Cannot decode authorization token. {}", ex.getMessage());
            return null;
        }
    }
}
