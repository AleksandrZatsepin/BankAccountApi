package ru.aston.bankaccountapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.bankaccountapi.entity.Account;
import ru.aston.bankaccountapi.entity.Transaction;
import ru.aston.bankaccountapi.repository.TransactionRepository;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    @Override
    public List<Transaction> getAllTransactionsByAccountNumber(UUID accountNumber) {
        Account account = accountService.getAccountByAccountNumber(accountNumber);
        return transactionRepository.findAllByAccounts(account.getAccountNumber().toString());
    }
}
