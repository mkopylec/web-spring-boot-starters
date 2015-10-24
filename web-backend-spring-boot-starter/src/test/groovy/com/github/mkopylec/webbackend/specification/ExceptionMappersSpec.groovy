package com.github.mkopylec.webbackend.specification

import com.github.mkopylec.webbackend.BasicSpec
import com.github.mkopylec.webbackend.app.mappers.RequestData
import com.github.mkopylec.webbackend.jersey.mappers.Error

import javax.ws.rs.core.GenericType

import static com.github.mkopylec.webbackend.app.Strings.APPLICATION_EXCEPTION_CODE
import static com.github.mkopylec.webbackend.app.Strings.APPLICATION_EXCEPTION_MESSAGE
import static com.github.mkopylec.webbackend.app.Strings.CUSTOM_EXCEPTION_MESSAGE
import static com.github.mkopylec.webbackend.app.Strings.EMPTY_CONTENT
import static com.github.mkopylec.webbackend.app.Strings.EXCEPTION_MESSAGE
import static com.github.mkopylec.webbackend.app.Strings.INVALID_NUMBER
import static com.github.mkopylec.webbackend.app.Strings.THROWABLE_MESSAGE
import static com.github.mkopylec.webbackend.assertions.CustomAssertions.assertThat

class ExceptionMappersSpec extends BasicSpec {

    def "Should fail with fatal error"() {
        when:
        def response = GET 'mappers/throwable'

        then:
        response.status == 500

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(1)
                .containsErrorFor('FATAL_ERROR', THROWABLE_MESSAGE, 'java.lang.Throwable', '/mappers/throwable')
    }

    def "Should fail with unexpected error"() {
        when:
        def response = GET 'mappers/exception'

        then:
        response.status == 500

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(1)
                .containsErrorFor('UNEXPECTED_ERROR', EXCEPTION_MESSAGE, 'java.lang.Exception', '/mappers/exception')
    }

    def "Should fail with HTTP error"() {
        when:
        def response = GET 'mappers/invalid'

        then:
        response.status == 404

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(1)
                .containsErrorFor('HTTP_ERROR', 'HTTP 404 Not Found', 'javax.ws.rs.NotFoundException', '/mappers/invalid')
    }

    def "Should fail with validation error"() {
        given:
        def requestData = new RequestData('', 0)

        when:
        def response = POST 'mappers/constraint', requestData

        then:
        response.status == 400

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(2)
                .containsErrorFor(INVALID_NUMBER, null, 'javax.validation.ConstraintViolationException', '/mappers/constraint')
                .containsErrorFor(EMPTY_CONTENT, null, 'javax.validation.ConstraintViolationException', '/mappers/constraint')
    }

    def "Should fail with application error"() {
        when:
        def response = GET 'mappers/application'

        then:
        response.status == 501

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(1)
                .containsErrorFor(APPLICATION_EXCEPTION_CODE, APPLICATION_EXCEPTION_MESSAGE, 'com.github.mkopylec.webbackend.app.mappers.ExceptionMappersEndpoint$1', '/mappers/application')
    }

    def "Should fail with custom error"() {
        when:
        def response = GET 'mappers/custom'

        then:
        response.status == 403

        def error = response.readEntity(String)
        error == CUSTOM_EXCEPTION_MESSAGE
    }
}