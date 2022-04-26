package com.example.bachelorproefbackend.configuration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InputNotValidException extends RuntimeException {
    public InputNotValidException() {
        super();
    }
    public InputNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
    public InputNotValidException(String message) {
        super(message);
    }
    public InputNotValidException(Throwable cause) {
        super(cause);
    }
}