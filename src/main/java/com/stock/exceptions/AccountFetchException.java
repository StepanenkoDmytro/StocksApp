package com.stock.exceptions;

public class AccountFetchException extends RuntimeException {
    public AccountFetchException(String message) {
        super(message);
    }
}
