package com.DigitalSettings.thermostat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MailAlreadyInUseException extends RuntimeException {
    public MailAlreadyInUseException(String message) {
        super("Mail address: " + message + " is already in use");
    }
}
