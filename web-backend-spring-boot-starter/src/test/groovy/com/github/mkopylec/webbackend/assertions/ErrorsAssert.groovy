package com.github.mkopylec.webbackend.assertions

import com.github.mkopylec.webbackend.jersey.mappers.Error

class ErrorsAssert {

    private List<Error> actual

    protected ErrorsAssert(List<Error> actual) {
        assert actual != null
        this.actual = actual
    }

    ErrorsAssert containsErrors(int numberOfErrors) {
        assert actual.size() == numberOfErrors
        return this
    }

    ErrorsAssert containsErrorFor(String errorCode, String message, String exception, String path) {
        assert 1 == actual.findAll {
            it.errorCode == errorCode &&
                    it.message == message &&
                    it.exception == exception &&
                    it.path == path
        }.size()
        return this
    }
}
