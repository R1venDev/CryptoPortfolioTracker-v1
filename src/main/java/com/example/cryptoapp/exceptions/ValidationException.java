package com.example.cryptoapp.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String message){
        super(message);
    }
    public ValidationException(String message, Throwable cause){
        super(message,cause);
    }
}
