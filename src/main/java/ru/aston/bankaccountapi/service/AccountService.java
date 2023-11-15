package ru.aston.bankaccountapi.service;



import ru.aston.bankaccountapi.entity.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account createAccount(String name, String pin);
    List<Account> getAllAccounts();
    Account getAccountByAccountNumber(UUID accountNumber);
    void deposit (UUID accountNumber, BigDecimal amount);

    void transfer(UUID fromAccountNumber, UUID toAccountNumber, BigDecimal amount, String pin);

    void withdraw(UUID accountNumber, BigDecimal amount, String pin);
}
