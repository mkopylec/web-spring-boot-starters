package com.github.mkopylec.webbackend.specification

import com.github.mkopylec.webbackend.BasicSpec
import com.github.mkopylec.webbackend.utils.TestLoggerFilter

import static com.github.mkopylec.webbackend.app.Constants.LOGGER_MESSAGE

class LoggingSpec extends BasicSpec {

    def "Should log message with random request ID"() {
        given:
        def filter = new TestLoggerFilter(LOGGER_MESSAGE)
        loggerFilter = filter

        when:
        def response = GET 'logging'

        then:
        response.status == 204

        filter.requestId.size() == 5
    }

    def "Should log message with request ID defined by incoming HTTP request"() {
        given:
        def filter = new TestLoggerFilter(LOGGER_MESSAGE)
        loggerFilter = filter

        when:
        def response = GET 'logging', ['X-MDC': '12345']

        then:
        response.status == 204

        filter.requestId == '12345'
    }

    void cleanup() {
        removeLoggerFilters()
    }
}