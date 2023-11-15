package ru.aston.bankaccountapi.controller;


import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.bankaccountapi.entity.Transaction;
import ru.aston.bankaccountapi.service.TransactionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {


    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping(value = "/{accountNumber}")
    public ResponseEntity<List<Transaction>> getAllTransactionsById(@PathVariable @NotBlank UUID accountNumber) {
        return ResponseEntity.ok(transactionService.getAllTransactionsByAccountNumber(accountNumber));
    }
}
