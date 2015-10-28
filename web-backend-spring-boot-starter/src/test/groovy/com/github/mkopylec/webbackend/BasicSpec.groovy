package com.github.mkopylec.webbackend

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.mkopylec.webbackend.app.BackendApplication
import com.github.mkopylec.webbackend.security.authorization.JsonWebToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.core.io.ClassPathResource
import org.springframework.security.jwt.crypto.sign.RsaSigner
import org.springframework.security.jwt.crypto.sign.Signer
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.Client
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.Response

import static java.time.LocalDateTime.now
import static java.time.ZoneOffset.UTC
import static javax.ws.rs.client.ClientBuilder.newClient
import static javax.ws.rs.client.Entity.entity
import static javax.ws.rs.core.MediaType.APPLICATION_JSON
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import static org.slf4j.LoggerFactory.getLogger
import static org.springframework.security.jwt.JwtHelper.encode

@WebIntegrationTest
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = BackendApplication)
abstract class BasicSpec extends Specification {

    @Shared
    private Client client = newClient()
    @Shared
    private ObjectMapper mapper = new ObjectMapper()
    @Shared
    private Signer signer = new RsaSigner(new ClassPathResource('token_key_private.pem').file.text)

    @Autowired
    private EmbeddedWebApplicationContext context

    protected Response GET(String path) {
        return getWebTarget(path)
                .request()
                .get()
    }

    protected Response GET(String path, String authorizationToken) {
        return getWebTarget(path)
                .request()
                .header('Authorization', "Bearer $authorizationToken")
                .get()
    }

    protected Response POST(String path, Object body) {
        return getWebTarget(path)
                .request()
                .post(entity(body, APPLICATION_JSON))
    }

    protected String validAuthorizationToken(List<String> authorities = []) {
        def expirationTime = now().plusMinutes(1).toEpochSecond(UTC)
        return generateAuthorizationToken(expirationTime, authorities)
    }

    protected String expiredAuthorizationToken(List<String> authorities = []) {
        def expirationTime = now().minusMinutes(1).toEpochSecond(UTC)
        return generateAuthorizationToken(expirationTime, authorities)
    }

    protected String invalidAuthorizationToken() {
        return randomAlphanumeric(30)
    }

    protected void setLoggerFilter(Filter<ILoggingEvent> filter) {
        Logger logger = (Logger) getLogger('ROOT')
        logger.getAppender('CONSOLE').addFilter(filter)
    }

    protected void removeLoggerFilters() {
        Logger logger = (Logger) getLogger('ROOT')
        logger.getAppender('CONSOLE').clearAllFilters()
    }

    private WebTarget getWebTarget(String path) {
        return client.target("http://localhost:$context.embeddedServletContainer.port/$path")
    }

    private String generateAuthorizationToken(long expirationTime, List<String> authorities) {
        def jwt = new JsonWebToken('user', expirationTime, authorities)
        return encode(mapper.writeValueAsString(jwt), signer).encoded
    }

    void cleanupSpec() {
        client.close()
    }
}
