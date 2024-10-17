package com.stock.service.exceptions;

public class AuthSourceException extends RuntimeException {
    public AuthSourceException(String message) {
        super(message);
    }
}
