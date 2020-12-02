package com.example.bankapp.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.bankapp.domain.Customer;

/**
 * Customer Repository Interface
 *
 * 
 */

public interface CustomerRepository extends CrudRepository<Customer, String> {

    /**
     * Find Customer by phone number.
     *
     * @param phonenumber phone number of the customer
     * @return Optional of customer
     */
    Optional<Customer> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
    
    Optional<Customer> findByEmailAddress(@Param("emailAddress")String emailAddress);

    @Override
    @RestResource(exported = false)
    <S extends Customer> S save(S s);

    @Override
    @RestResource(exported = false)
    <S extends Customer> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    void deleteById(String s);

    @Override
    @RestResource(exported = false)
    void delete(Customer customer);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Customer> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}

