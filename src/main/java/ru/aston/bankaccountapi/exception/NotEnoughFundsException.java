package ru.aston.bankaccountapi.exception;

public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException() {
        super("Not Enough Funds");
    }
}