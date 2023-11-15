package ru.aston.bankaccountapi.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.bankaccountapi.dto.AccountCreatingRequest;
import ru.aston.bankaccountapi.dto.PaymentDto;
import ru.aston.bankaccountapi.entity.Account;
import ru.aston.bankaccountapi.service.AccountService;
import ru.aston.bankaccountapi.service.AccountServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
        private final AccountService accountService;

        public AccountController(AccountServiceImpl accountService) {
            this.accountService = accountService;
        }

        @GetMapping
        public ResponseEntity<List<Account>> getAllAccounts() {
            return ResponseEntity.ok(accountService.getAllAccounts());
        }


        @GetMapping(value = "/{accountNumber}")
        public ResponseEntity<Account> getAccount(@PathVariable @NotBlank UUID accountNumber) {
            return ResponseEntity.ok(
                    accountService.getAccountByAccountNumber(accountNumber)
            );
        }

        @PostMapping()
        public ResponseEntity<UUID> saveAccount(@RequestBody @Valid AccountCreatingRequest accountCreatingRequest) {
            return ResponseEntity.ok(
                    accountService.createAccount(accountCreatingRequest.getName(), accountCreatingRequest.getPin()).getAccountNumber()
            );
        }

        @PatchMapping(value = "/{accountNumber}/deposit")
        public ResponseEntity<String> deposit(@PathVariable @NotBlank UUID accountNumber, @RequestBody @Valid PaymentDto paymentDto) {
                accountService.deposit(accountNumber, paymentDto.getAmount());
            return ResponseEntity.ok("Success deposit");
        }

        @PatchMapping(value = "/{fromAccountNumber}/transfer/{toAccountNumber}")
        public ResponseEntity<String> transfer(@PathVariable @NotBlank UUID fromAccountNumber,
                                               @PathVariable @NotBlank UUID toAccountNumber,
                                               @RequestBody @Valid PaymentDto paymentDto) {
                accountService.transfer(fromAccountNumber, toAccountNumber, paymentDto.getAmount(), paymentDto.getPin());
            return new ResponseEntity<>("Success transfer", HttpStatus.OK);
        }

        @PatchMapping(value = "/{accountNumber}/withdraw")
        public ResponseEntity<String> withdraw(@PathVariable @NotBlank UUID accountNumber,
                                               @RequestBody  @Valid PaymentDto paymentDto) throws RuntimeException{
            accountService.withdraw(accountNumber, paymentDto.getAmount(), paymentDto.getPin());
            return new ResponseEntity<>("Success withdraw", HttpStatus.OK);
        }

}
