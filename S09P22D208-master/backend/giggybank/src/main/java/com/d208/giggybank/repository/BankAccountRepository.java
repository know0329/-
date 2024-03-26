package com.d208.giggybank.repository;

import com.d208.giggybank.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import javax.transaction.Transactional;
import java.util.Optional;

//@Transactional
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByAccountNumber(String accountnumber);
}
