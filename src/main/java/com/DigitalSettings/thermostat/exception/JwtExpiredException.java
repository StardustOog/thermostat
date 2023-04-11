package com.DigitalSettings.thermostat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JwtExpiredException extends RuntimeException{
    public JwtExpiredException() {
        super("Expired token");
    }
}
