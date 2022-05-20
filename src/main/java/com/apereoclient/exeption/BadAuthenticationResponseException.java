package com.apereoclient.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadAuthenticationResponseException extends RuntimeException{

    public BadAuthenticationResponseException(String message) {
        super(message);
    }

}
