package com.d208.giggybank.service;

import com.d208.giggybank.domain.BankAccount;
import com.d208.giggybank.domain.Customer;
import com.d208.giggybank.dto.TestDto;
import com.d208.giggybank.repository.BankAccountRepository;
import com.d208.giggybank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;

    public void testService(TestDto testDto) {
        Customer customer = customerRepository.save(Customer.builder()
                .name(testDto.getName())
                .birthday(testDto.getBirthday())
                .build());

        bankAccountRepository.save(BankAccount.builder()
                        .customer(customer)
                .accountNumber(testDto.getAccountNumber())
                .balance(testDto.getBalance())
                .build());

    }
}
