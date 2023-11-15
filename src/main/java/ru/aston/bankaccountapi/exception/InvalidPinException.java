package ru.aston.bankaccountapi.exception;

public class InvalidPinException extends RuntimeException{
    public InvalidPinException(String message) {
        super(message);
    }
}
