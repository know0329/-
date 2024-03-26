package com.d208.giggybank.domain;


import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BankAccount {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="CUSTOMER_ID")
    private Customer customer;

    @Builder.Default
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    private List<BankAccountHistory> bankAccountHistories = new ArrayList<>();


    private String accountNumber;

    private int balance;

    public void updateBalance(int newBalance) {
        this.balance = newBalance;
    }

}
