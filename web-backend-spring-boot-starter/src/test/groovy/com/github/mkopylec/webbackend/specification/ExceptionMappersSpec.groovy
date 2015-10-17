package com.github.mkopylec.webbackend.specification

import com.github.mkopylec.webbackend.BasicSpec
import com.github.mkopylec.webbackend.jersey.mappers.Error

import javax.ws.rs.core.GenericType

import static com.github.mkopylec.webbackend.app.Strings.THROWABLE_MESSAGE
import static com.github.mkopylec.webbackend.assertions.CustomAssertions.assertThat

class ExceptionMappersSpec extends BasicSpec {

    def "Should fail with fatal error"() {
        when:
        def response = getWebTarget('mappers/throwable')
                .request()
                .get()

        then:
        response.status == 500

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(1)
                .containsErrorFor('FATAL_ERROR', THROWABLE_MESSAGE, 'java.lang.Throwable', '/mappers/throwable')
    }
}