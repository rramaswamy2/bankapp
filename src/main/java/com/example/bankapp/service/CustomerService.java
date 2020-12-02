package com.example.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankapp.domain.Customer;
import com.example.bankapp.repo.CustomerRepository;

/**
 * Customer Service
 * 
 */
@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    /**
     * Create a customer
     *
     * @param phoneNumber phone number of customer 
     * @param name name of customer
     *
     * @return new or existing customer
     */
    public Customer createCustomer(String name, String address, String phoneNumber, String emailAddress, String gender, String dateOfBirth, String customerType) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .orElse(customerRepository.save(new Customer(name, address, phoneNumber, emailAddress, gender, dateOfBirth, customerType)));
    }

    /**
     * Lookup All customers
     *
     * @return iterable list of customers
     */
    public Iterable<Customer> lookup(){
        return customerRepository.findAll();
    }

    public long total() {
        return customerRepository.count();
    }
}

