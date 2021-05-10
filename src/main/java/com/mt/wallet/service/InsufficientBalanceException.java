package com.mt.wallet.service;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String errorMessage) {
        super(errorMessage);
    }
}
