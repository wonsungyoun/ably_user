package com.subutai.customer.exception;

public class SmsSendHistoryNotExistException extends RuntimeException{

    public SmsSendHistoryNotExistException(String message) {
        super(message);
    }
}
