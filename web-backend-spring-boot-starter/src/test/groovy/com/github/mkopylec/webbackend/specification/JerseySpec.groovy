package com.github.mkopylec.webbackend.specification

import com.github.mkopylec.webbackend.BasicSpec
import com.github.mkopylec.webbackend.app.jersey.RequestData
import com.github.mkopylec.webbackend.app.jersey.ResponseData
import com.github.mkopylec.webbackend.jersey.mappers.Error

import javax.ws.rs.core.GenericType

import static com.github.mkopylec.webbackend.app.Constants.APPLICATION_EXCEPTION_CODE
import static com.github.mkopylec.webbackend.app.Constants.APPLICATION_EXCEPTION_MESSAGE
import static com.github.mkopylec.webbackend.app.Constants.CUSTOM_EXCEPTION_MESSAGE
import static com.github.mkopylec.webbackend.app.Constants.EMPTY_CONTENT
import static com.github.mkopylec.webbackend.app.Constants.EXCEPTION_MESSAGE
import static com.github.mkopylec.webbackend.app.Constants.INVALID_NUMBER
import static com.github.mkopylec.webbackend.app.Constants.RESPONSE_DATA_NUMBER
import static com.github.mkopylec.webbackend.app.Constants.THROWABLE_MESSAGE
import static com.github.mkopylec.webbackend.assertions.CustomAssertions.assertThat

class JerseySpec extends BasicSpec {

    def "Should get response data"() {
        when:
        def response = GET 'jersey/data'

        then:
        response.status == 200

        def data = response.readEntity(ResponseData)
        data != null
        data.number == RESPONSE_DATA_NUMBER
    }

    def "Should fail with fatal error"() {
        when:
        def response = GET 'jersey/throwable'

        then:
        response.status == 500

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(1)
                .containsErrorFor('FATAL_ERROR', THROWABLE_MESSAGE, 'java.lang.Throwable', '/jersey/throwable')
    }

    def "Should fail with unexpected error"() {
        when:
        def response = GET 'jersey/exception'

        then:
        response.status == 500

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(1)
                .containsErrorFor('UNEXPECTED_ERROR', EXCEPTION_MESSAGE, 'java.lang.Exception', '/jersey/exception')
    }

    def "Should fail with HTTP error"() {
        when:
        def response = GET 'jersey/invalid'

        then:
        response.status == 404

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(1)
                .containsErrorFor('HTTP_ERROR', 'HTTP 404 Not Found', 'javax.ws.rs.NotFoundException', '/jersey/invalid')
    }

    def "Should fail with validation error"() {
        given:
        def requestData = new RequestData('', 0)

        when:
        def response = POST 'jersey/constraint', requestData

        then:
        response.status == 400

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(2)
                .containsErrorFor(INVALID_NUMBER, null, 'javax.validation.ConstraintViolationException', '/jersey/constraint')
                .containsErrorFor(EMPTY_CONTENT, null, 'javax.validation.ConstraintViolationException', '/jersey/constraint')
    }

    def "Should fail with application error"() {
        when:
        def response = GET 'jersey/application'

        then:
        response.status == 501

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(1)
                .containsErrorFor(APPLICATION_EXCEPTION_CODE, APPLICATION_EXCEPTION_MESSAGE, 'com.github.mkopylec.webbackend.app.jersey.JerseyEndpoint$1', '/jersey/application')
    }

    def "Should fail with custom error"() {
        when:
        def response = GET 'jersey/custom'

        then:
        response.status == 403

        def error = response.readEntity(String)
        error == CUSTOM_EXCEPTION_MESSAGE
    }
}