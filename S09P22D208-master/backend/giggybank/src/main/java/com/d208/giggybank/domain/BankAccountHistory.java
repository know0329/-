package com.d208.giggybank.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BankAccountHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CUSTOMER_ID")
    private Customer customer;

//    @JsonIgnore
    //나중에 프론트로 데이터 줄 때 안 보내줌
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BANK_ACCOUNT_ID")
    private BankAccount bankAccount;

    private int amount;

    public void updateAmount(int newAmount, BankAccount bankAccount) {
        this.amount = newAmount;
        bankAccount.updateBalance(newAmount);
    }

    private LocalDateTime transactionDate;

    private String transactionType;

    private int deposit;

    private int withdraw;

    private String content;


}
