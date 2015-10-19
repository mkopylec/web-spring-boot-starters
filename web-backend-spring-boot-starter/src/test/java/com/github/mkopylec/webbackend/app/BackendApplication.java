package com.github.mkopylec.webbackend.app;

import com.github.mkopylec.webbackend.WebBackendApplication;

import static org.springframework.boot.SpringApplication.run;

@WebBackendApplication
public class BackendApplication {

    public static void main(String[] args) {
        run(BackendApplication.class, args);
    }
}
