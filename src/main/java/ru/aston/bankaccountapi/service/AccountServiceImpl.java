package ru.aston.bankaccountapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.bankaccountapi.entity.Account;
import ru.aston.bankaccountapi.entity.Transaction;
import ru.aston.bankaccountapi.entity.TransactionType;
import ru.aston.bankaccountapi.exception.InvalidPinException;
import ru.aston.bankaccountapi.exception.NotEnoughFundsException;
import ru.aston.bankaccountapi.exception.NotFoundException;
import ru.aston.bankaccountapi.repository.AccountRepository;
import ru.aston.bankaccountapi.repository.TransactionRepository;


import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Account createAccount(String name, String pin) {
        return accountRepository.save(Account.builder().name(name).pin(pin).transactions(new HashSet<>()).amount(BigDecimal.valueOf(0)).build());
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountByAccountNumber(UUID accountNumber) {
        //TODO:
       return accountRepository.findById(accountNumber).orElseThrow(()->
                new NotFoundException("Account not found"));
    }

    @Override
    @Transactional
    public void deposit(UUID accountNumber, BigDecimal amount) {

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(()->new NotFoundException("Account with id not found"));

        Transaction transaction = Transaction.builder()
                .time(LocalTime.now())
                .amount(amount).transactionType(TransactionType.DEPOSIT)
                .accounts(new HashSet<>())
                .build();

        transaction.getAccounts().add(account);
        account.setAmount(account.getAmount().add(amount));
        account.getTransactions().add(transaction);
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void transfer(UUID senderAccountNumber, UUID toAccountNumber, BigDecimal amount, String pin) {
        Account sender = accountRepository.findById(senderAccountNumber)
                .orElseThrow(()->new NotFoundException("Account with id not found"));

        Account reciever = accountRepository.findById(toAccountNumber)
                .orElseThrow(()->new NotFoundException("Account with id not found"));

        if (!(sender.getPin().equals(pin))) {
            throw new InvalidPinException("Pin is not verified");
        }
        if (sender.getAmount().compareTo(amount) < 0) {
            throw new NotEnoughFundsException();
        }



        Transaction transaction = Transaction.builder()
                .time(LocalTime.now())
                .accounts(new HashSet<>())
                .amount(amount)
                .info("Transfer from " + sender.getAccountNumber() +" to " +  reciever.getAccountNumber())
                .transactionType(TransactionType.TRANSFER).build();
        transaction.getAccounts().add(reciever);
        transaction.getAccounts().add(sender);

        sender.setAmount(sender.getAmount().subtract(amount));
        reciever.setAmount(reciever.getAmount().add(amount));

        sender.getTransactions().add(transaction);
        reciever.getTransactions().add(transaction);

        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void withdraw(UUID accountNumber, BigDecimal amount, String pin) {

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(()->new NotFoundException("Account with id not found"));

        if (account.getAmount().compareTo(amount) < 0) {
            throw new NotEnoughFundsException();
        }
        if (!(account.getPin().equals(pin))) {
            throw new InvalidPinException("Pin is not verified");
        }

        Transaction transaction = Transaction.builder()
                .time(LocalTime.now())
                .amount(amount)
                .accounts(new HashSet<>())
                .transactionType(TransactionType.WITHDRAW).build();
        transaction.getAccounts().add(account);


        account.setAmount(account.getAmount().subtract(amount));
        account.getTransactions().add(transaction);

        transactionRepository.save(transaction);
    }


}
