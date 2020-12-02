package com.example.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankapp.domain.BankAccount;
import com.example.bankapp.domain.Customer;
import com.example.bankapp.repo.BankAccountRepository;
import com.example.bankapp.repo.CustomerRepository;

/**
 * bank account  Service
 *
 */
@Service
public class BankAccountService {
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Create a new bank account Object and persist it to the Database
     *
     * @param phoneNumber phoneNumber of the customer
     * @param initialAmount initial deposit amount for the account
    
     * @return BankAccount
     */
    public BankAccount createBankAccount(String phoneNumber, double initialAmount) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new RuntimeException("Customer does not exist for phoneNumber : " + phoneNumber));
        
       // String accountId = UUID.randomUUID().toString();
        return bankAccountRepository.save(new BankAccount(customer.getCustomerId(), initialAmount));
    }
    /**
     * Calculate the number of bank accounts in the Database.
     *
     * @return the total.
     */
    public long total() {
        return bankAccountRepository.count();
    }
}

