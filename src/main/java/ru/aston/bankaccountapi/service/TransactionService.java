package ru.aston.bankaccountapi.service;


import ru.aston.bankaccountapi.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionsByAccountNumber(UUID accountNumber);
}