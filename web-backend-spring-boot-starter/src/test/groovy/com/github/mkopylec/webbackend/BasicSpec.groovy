package com.github.mkopylec.webbackend

import com.github.mkopylec.webbackend.app.BackendApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.Client
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.Response

import static javax.ws.rs.client.ClientBuilder.newClient
import static javax.ws.rs.client.Entity.entity
import static javax.ws.rs.core.MediaType.APPLICATION_JSON

@WebIntegrationTest
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = BackendApplication)
abstract class BasicSpec extends Specification {

    @Shared
    private Client client = newClient()
    @Autowired
    private EmbeddedWebApplicationContext context

    protected Response GET(String path) {
        return getWebTarget(path)
                .request()
                .get()
    }

    protected Response POST(String path, Object body) {
        return getWebTarget(path)
                .request()
                .post(entity(body, APPLICATION_JSON))
    }

    private WebTarget getWebTarget(String path) {
        return client.target("http://localhost:$context.embeddedServletContainer.port/$path")
    }

    void cleanupSpec() {
        client.close()
    }
}
