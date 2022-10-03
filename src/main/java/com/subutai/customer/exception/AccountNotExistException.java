package com.subutai.customer.exception;

public class AccountNotExistException extends RuntimeException {

    public AccountNotExistException(String message) {
        super(message);
    }
}
