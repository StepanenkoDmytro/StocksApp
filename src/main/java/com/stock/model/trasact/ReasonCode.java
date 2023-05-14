package com.stock.model.trasact;

public enum ReasonCode {
    //DEPOSIT ACCOUNT

    DEPOSIT_SUCCESS("Deposit Transaction Successful"),
    DEPOSIT_FAILED("Deposit Transaction Failed"),
    INSUFFICIENT_FUNDS("Insufficient Funds for Deposit"),
    INVALID_ACCOUNT_DEPOSIT("Invalid Account for Deposit"),

    //MONEY TRANSFER:
    TRANSFER_SUCCESS("Transfer Transaction Successful"),
    TRANSFER_FAILED("Transfer Transaction Failed"),
    INVALID_ACCOUNT_TRANSFER("Invalid Account for Transfer"),
    INSUFFICIENT_FUNDS_MONEY("Insufficient Funds for Transfer"),
    DUPLICATE_TRANSFER("Duplicate Transfer Transaction"),

//    BUY CRYPTO:
    BUY_CRYPTO_SUCCESS("Cryptocurrency Purchase Successful"),
    BUY_CRYPTO_FAILED("Cryptocurrency Purchase Failed"),
    INSUFFICIENT_FUNDS_COINS("Insufficient Funds for Cryptocurrency Purchase"),
    INVALID_CRYPTO("Invalid Cryptocurrency for Purchase"),
    INVALID_PRICE_COIN("Invalid Price for Cryptocurrency Purchase"),

//    BUY STOCKS:
    BUY_STOCKS_SUCCESS("Stocks Purchase Successful"),
    BUY_STOCKS_FAILED("Stocks Purchase Failed"),
    INVALID_STOCK("Invalid Stock for Purchase"),
    INSUFFICIENT_FUNDS_STOCK("Insufficient Funds for Stocks Purchase"),
    INVALID_PRICE_STOCK("Invalid Price for Stocks Purchase"),
//    Купівля облігацій:

    BUY_BONDS_SUCCESS("Bonds Purchase Successful"),
    BUY_BONDS_FAILED("Bonds Purchase Failed"),
    INSUFFICIENT_FUNDS_BOND("Insufficient Funds for Bonds Purchase"),
    INVALID_BOND("Invalid Bond for Purchase"),
    INVALID_PRICE("Invalid Price for Bonds Purchase");

    private final String stringValue;

    ReasonCode(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
