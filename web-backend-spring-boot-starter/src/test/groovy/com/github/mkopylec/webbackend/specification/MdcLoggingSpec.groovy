package com.github.mkopylec.webbackend.specification

import com.github.mkopylec.webbackend.BasicSpec
import com.github.mkopylec.webbackend.utils.TestLoggerFilter

import static com.github.mkopylec.webbackend.app.Strings.LOGGER_MESSAGE

class MdcLoggingSpec extends BasicSpec {

    def "Should log message with request ID"() {
        given:
        def filter = new TestLoggerFilter(LOGGER_MESSAGE)
        loggerFilter = filter

        when:
        def response = GET 'logging'

        then:
        response.status == 204

        filter.requestId.size() == 5
    }

    void cleanup() {
        removeLoggerFilters()
    }
}