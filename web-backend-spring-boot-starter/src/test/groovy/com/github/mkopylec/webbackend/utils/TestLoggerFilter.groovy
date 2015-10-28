package com.github.mkopylec.webbackend.utils

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply

import static ch.qos.logback.core.spi.FilterReply.NEUTRAL

class TestLoggerFilter extends Filter<ILoggingEvent> {

    private final String messageToCatch
    private String requestId

    TestLoggerFilter(String messageToCatch) {
        this.messageToCatch = messageToCatch
    }

    @Override
    FilterReply decide(ILoggingEvent event) {
        if (event.message == messageToCatch) {
            requestId = event.MDCPropertyMap.requestId
        }
        return NEUTRAL
    }

    String getRequestId() {
        return requestId
    }
}
