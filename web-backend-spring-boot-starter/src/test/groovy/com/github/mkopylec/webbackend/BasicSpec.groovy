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

import static javax.ws.rs.client.ClientBuilder.newClient

@WebIntegrationTest
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = BackendApplication)
abstract class BasicSpec extends Specification {

    @Shared
    private Client client = newClient()
    @Autowired
    private EmbeddedWebApplicationContext context

    protected WebTarget getWebTarget(String path) {
        return client.target("http://localhost:$context.embeddedServletContainer.port/$path")
    }

    void cleanupSpec() {
        client.close()
    }
}
