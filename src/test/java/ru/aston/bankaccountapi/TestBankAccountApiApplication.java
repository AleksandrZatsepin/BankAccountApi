package ru.aston.bankaccountapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestBankAccountApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(BankAccountApiApplication::main).with(TestBankAccountApiApplication.class).run(args);
    }

}
