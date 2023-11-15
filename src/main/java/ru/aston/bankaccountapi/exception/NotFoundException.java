package ru.aston.bankaccountapi.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String string) {
        super(string);
    }
}
