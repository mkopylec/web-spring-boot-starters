package com.github.mkopylec.webbackend.assertions

import com.github.mkopylec.webbackend.jersey.mappers.Error

class CustomAssertions {

    static ErrorsAssert assertThat(List<Error> actual) {
        return new ErrorsAssert(actual)
    }
}
