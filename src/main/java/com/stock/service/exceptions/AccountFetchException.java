package com.stock.service.exceptions;

public class AccountFetchException extends RuntimeException {
    public AccountFetchException(String message) {
        super(message);
    }
}
