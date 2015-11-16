package com.github.mkopylec.webbackend.security.ignore;

import java.util.List;

public interface SecuredEndpointsScanner {

    List<String> scanForSecuredEndpoints();
}
