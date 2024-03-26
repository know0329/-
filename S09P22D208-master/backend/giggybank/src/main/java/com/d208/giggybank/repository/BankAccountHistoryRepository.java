package com.d208.giggybank.repository;

import com.d208.giggybank.domain.BankAccount;
import com.d208.giggybank.domain.BankAccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transaction;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BankAccountHistoryRepository extends JpaRepository<BankAccountHistory, Long> {

    List<BankAccountHistory> findByBankAccountId(Long bankAccountId);

    @Query(value = "select bankAccountHistory from BankAccountHistory bankAccountHistory " +
            "where bankAccountHistory.bankAccount = :bankAccount AND " +
            "bankAccountHistory.transactionDate BETWEEN :startDate AND :endDate")
    List<BankAccountHistory> findByBankAccountAndTransactionDateTimeBetween(
            @Param("bankAccount") BankAccount bankAccount,  @Param("startDate") LocalDateTime startDate,  @Param("endDate") LocalDateTime endDate);

}
