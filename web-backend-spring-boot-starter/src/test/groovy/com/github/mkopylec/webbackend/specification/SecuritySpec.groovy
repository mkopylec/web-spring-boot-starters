package com.github.mkopylec.webbackend.specification

import com.github.mkopylec.webbackend.BasicSpec
import com.github.mkopylec.webbackend.app.security.ResponseData
import com.github.mkopylec.webbackend.jersey.mappers.Error
import spock.lang.Unroll

import javax.ws.rs.core.GenericType

import static com.github.mkopylec.webbackend.app.Constants.RESPONSE_DATA_MESSAGE
import static com.github.mkopylec.webbackend.assertions.CustomAssertions.assertThat

class SecuritySpec extends BasicSpec {

    def "Should get response data when authenticated"() {
        given:
        def authorizationToken = validAuthorizationToken()

        when:
        def response = GET 'security/authenticated', [Authorization: "Bearer $authorizationToken"]

        then:
        response.status == 200

        def data = response.readEntity(ResponseData)
        data != null
        data.message == RESPONSE_DATA_MESSAGE
    }

    def "Should get response data when authenticated with user role"() {
        given:
        def authorizationToken = validAuthorizationToken(['USER'])

        when:
        def response = GET 'security/authority', [Authorization: "Bearer $authorizationToken"]

        then:
        response.status == 200

        def data = response.readEntity(ResponseData)
        data != null
        data.message == RESPONSE_DATA_MESSAGE
    }

    def "Should not get response data when authenticated with role different than user"() {
        given:
        def authorizationToken = validAuthorizationToken(['ADMIN'])

        when:
        def response = GET 'security/authority', [Authorization: "Bearer $authorizationToken"]

        then:
        response.status == 401

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(1)
                .containsErrorFor('SECURITY_ERROR', 'Access is denied', 'org.springframework.security.access.AccessDeniedException', '/security/authority')
    }

    @Unroll
    def "Should not get response data with #text authorization token"() {
        when:
        def response = GET 'security/authenticated', [Authorization: "Bearer $authorizationToken"]

        then:
        response.status == 401

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(1)
                .containsErrorFor('SECURITY_ERROR', 'Access is denied', 'org.springframework.security.access.AccessDeniedException', '/security/authenticated')

        where:
        authorizationToken          | text
        null                        | 'empty'
        invalidAuthorizationToken() | 'invalid'
        expiredAuthorizationToken() | 'expired'
    }
}