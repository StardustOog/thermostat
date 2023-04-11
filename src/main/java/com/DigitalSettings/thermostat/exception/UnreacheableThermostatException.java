package com.DigitalSettings.thermostat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnreacheableThermostatException extends RuntimeException {
    public UnreacheableThermostatException(String message) {
        super(message);
    }
}
