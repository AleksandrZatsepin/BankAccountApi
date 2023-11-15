package ru.aston.bankaccountapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aston.bankaccountapi.entity.Account;


import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
