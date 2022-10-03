package com.subutai.customer.exception;

public class CertificationExpireException extends RuntimeException{

    public CertificationExpireException(String message){
        super(message);
    }
}
