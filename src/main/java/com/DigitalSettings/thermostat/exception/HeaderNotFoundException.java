package com.DigitalSettings.thermostat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class HeaderNotFoundException extends RuntimeException{
    public HeaderNotFoundException(String message) {
        super(message);
    }
}
