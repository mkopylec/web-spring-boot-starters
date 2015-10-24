package com.github.mkopylec.webbackend.specification

import com.github.mkopylec.webbackend.BasicSpec
import com.github.mkopylec.webbackend.app.authorization.ResponseData
import com.github.mkopylec.webbackend.jersey.mappers.Error

import javax.ws.rs.core.GenericType

import static com.github.mkopylec.webbackend.app.Strings.RESPONSE_DATA_MESSAGE
import static com.github.mkopylec.webbackend.assertions.CustomAssertions.assertThat

class TokenAuthorizationSpec extends BasicSpec {

    def "Should get response data when authenticated"() {
        when:
        def response = GET 'authorization/authenticated'

        then:
        response.status == 200

        def data = response.readEntity(ResponseData)
        data != null
        data.message == RESPONSE_DATA_MESSAGE
    }

    def "Should not get response data when not authenticated"() {
        when:
        def response = GET 'authorization/authenticated'

        then:
        response.status == 401

        def errors = response.readEntity(new GenericType<List<Error>>() {})
        assertThat(errors)
                .containsErrors(1)
                .containsErrorFor('SECURITY_ERROR', 'Access is denied', 'org.springframework.security.access.AccessDeniedException', '/authorization/authenticated')
    }
}