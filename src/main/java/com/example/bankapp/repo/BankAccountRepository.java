package com.example.bankapp.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.bankapp.domain.BankAccount;


/**
 * Bank Account Repository Interface
 *
 
 */
//@RepositoryRestResource(exported = false)
@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
public interface BankAccountRepository extends PagingAndSortingRepository<BankAccount, String> {
    /**
     * Find bank account associated with the customer.
     *
     * @param customerId customer ID holding the bank account
     * @return Bank Account of the customer
     */
    List<BankAccount> findByCustomerId(@Param("customerId") String customerId);
    
   // Optional<BankAccount> findByAccountIdAndCustomerId(String accountId, String customerId);

    /**
     * Only return the main fields of a Tour, not the details
     *
     * @param code tour package code
     * @return tours without details
     */
    @Query(value = "{'accountId' : ?0 }",
            fields = "{ 'accountId':1, 'accountBalance':1}")
    BankAccount findAccountBalanceSummaryByAccountId(@Param("accountId") String accountId);

    @Override
    @RestResource(exported = false)
    <S extends BankAccount> S save(S s);

    @Override
    @RestResource(exported = false)
    <S extends BankAccount> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    void deleteById(String string);

    @Override
    @RestResource(exported = false)
    void delete(BankAccount bankAccount);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends BankAccount> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}

