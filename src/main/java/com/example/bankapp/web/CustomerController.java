package com.example.bankapp.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankapp.domain.BankAccount;
import com.example.bankapp.domain.Customer;
import com.example.bankapp.repo.BankAccountRepository;
import com.example.bankapp.repo.CustomerRepository;

/**
 * customer and bank account Controller
 *
*/
@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerController(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
    }

    protected CustomerController() {

    }

    /**
     * Create a customer.
     *
     * 
     * @param customer customer object
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @Validated Customer customer) {
        
        return customerRepository.save(new Customer(customer.getName(), customer.getAddress(), customer.getPhoneNumber(), customer.getEmailAddress(), customer.getGender(), customer.getDateOfBirth(), customer.getCustomerType()));
    }
    
    
    /**
     * Create a bank account for a customer and linking a account with a customer
     *
     * 
     * @param account bank account object
     */
    @PostMapping({"/{customerId}/account"})
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccount createBankAccount(@PathVariable String customerId, @RequestBody @Validated BankAccount account) {
        
        return bankAccountRepository.save(new BankAccount(customerId, account.getAccountBalance(), account.getAccountType(), account.getDetails()));
    }

    /**
     * Get all bank accounts for a customer.
     *
     * @param customerId customer identifier
     * 
     * @return A list of bank accounts
     */
    @GetMapping("/{customerId}/accounts")
    public List<BankAccount> getAccountsForCustomer(@PathVariable(value = "customerId") String customerId){
        return bankAccountRepository.findByCustomerId(customerId);
    }

    /**
     * Calculate the sum balance of all accounts for a customer.
     *
     * @param customerId customer identifier
     * @return Tuple of "sum" and the sum value.
     */
    @GetMapping(path = "/{customerId}/account/total")
    public Map<String, Double> getTotalAccountBalance(@PathVariable(value = "customerId") String customerId) {
        verifyCustomer(customerId);
        
        return Map.of("accountBalance",bankAccountRepository.findByCustomerId(customerId).stream()
                .mapToDouble(BankAccount::getAccountBalance).sum());
                //.orElseThrow(() ->
                //new NoSuchElementException("Customer has no account")));
    }
    
    
    @GetMapping(path = "/account/{accountId}/calculateAnnualInterest")
    public Map<String, Double> calculateInterestAndUpdateBalance(@PathVariable(value = "accountId") String accountId) {
    	verifyBankAccount(accountId);
    	
    	 Optional<BankAccount> optionalAccount = bankAccountRepository.findById(accountId);
    	 
    	 if(optionalAccount.isPresent()) {
    		 BankAccount account = optionalAccount.get();
    		 
    		 double currentBalance = account.getAccountBalance();
    		 
    		 double updatedBalance = currentBalance + (account.getInterestRate() * currentBalance * 1 /100);
    		 
    		 return Map.of("accountBalance", updatedBalance);
    	 } else {
    		 
    		 throw new NoSuchElementException("bank account does not exist");
    	 }
   	
    }

    
    
    /**
     * Update customer
     *
     * @param customerId customer identifier
     * @param customer customer Object
     * @return The modified customer object.
     */
    @PatchMapping(path = "/{customerId}")
    public Customer updateCustomerWithPatch(@PathVariable(value = "customerId") String customerId,
                                    @RequestBody @Validated Customer inputCustomer) {
        Customer customer = verifyCustomer(customerId);
        if(inputCustomer.getName() != null) {
        customer.setName(inputCustomer.getName());
        }
        if(inputCustomer.getEmailAddress() != null) {
        customer.setEmailAddress(inputCustomer.getEmailAddress());
        }
        if(inputCustomer.getAddress() != null) {
        customer.setAddress(inputCustomer.getAddress());
        }
        if(inputCustomer.getPhoneNumber() != null) {  // phone number normally does not change frequently
        	customer.setPhoneNumber(inputCustomer.getPhoneNumber());
        }
        if(inputCustomer.getDateOfBirth() != null) {
        	customer.setDateOfBirth(inputCustomer.getDateOfBirth()); // date of birth also does not change
        }
        return customerRepository.save(customer);
    }
    
    

    /**
     * Update balance for a bank account
     *
     * @param customerId customer identifier
     * @param tourRatin rating Data Transfer Object
     * @return The modified Rating DTO.
     */
    @PutMapping(path = "/account")
    public BankAccount updateWithPut(@RequestBody @Validated BankAccount inputAccount) {
        BankAccount account = verifyBankAccount(inputAccount.getAccountId());
        account.setAccountBalance(inputAccount.getAccountBalance());
        account.setAccountType(inputAccount.getAccountType());
        account.setInterestRate(inputAccount.getInterestRate());
        return bankAccountRepository.save(account);
    }
    /**
     * Update balance for a bank account
     *
     * @param customerId customer identifier
     * @param inputAccount account Object
     * @return The modified account
     */
    @PatchMapping(path = "/account")
    public BankAccount updateWithPatch(
                                      @RequestBody @Validated BankAccount inputAccount) {
        BankAccount account = verifyBankAccount(inputAccount.getAccountId());
        if (account.getAccountBalance() != null) {
            account.setAccountBalance(inputAccount.getAccountBalance());
        }
       
        return bankAccountRepository.save(account);
    }
    
    
    /**
     * transfer money from one bank account to another account
     *
     * @param sourceAccountId source bank account identifier
     * @param destinationAccountId destination account identifier
     * @param inputAccount account Object
     * @return The modified account
     */
    @PatchMapping(path = "/account/transfer/source/{sourceAccountId}/destination/{destinationAccountId}")
    public List<BankAccount> updateWithPatch(@PathVariable(value = "sourceAccountId") String sourceAccountId,
                                       @PathVariable(value = "destinationAccountId") String destinationAccountId,
                                      @RequestBody @Validated BankAccount inputAccount) {
        BankAccount account1 = verifyBankAccount(sourceAccountId);
        BankAccount account2 = verifyBankAccount(destinationAccountId);
        
        // check if source account has sufficient funds to transfer compared with the requested transfer amount
        if (account1.getAccountBalance() >= inputAccount.getAccountBalance()) {
            account2.setAccountBalance(account2.getAccountBalance() + inputAccount.getAccountBalance());
            account1.setAccountBalance(account1.getAccountBalance() - inputAccount.getAccountBalance());
        } else {
        	
        	throw new RuntimeException("Insufficient funds to transfer from source account to destination account");
        }
       
        List<BankAccount> accounts = new ArrayList<BankAccount>();
        accounts.add(account1);
        accounts.add(account2);
        bankAccountRepository.save(account1);
        bankAccountRepository.save(account2);
        return accounts;
        
    }
    
   

    /**
     * Delete a account of a customer
     *
     * @param customerId customer identifier
     * @param customerId customer identifier
     */
    @DeleteMapping(path = "/account/{accountId}")
    public void deleteAccount(@PathVariable(value = "accountId") String accountId) {
        BankAccount account = verifyBankAccount(accountId);
        bankAccountRepository.delete(account);
    }
    
    @DeleteMapping(path = "/{customerId}")
    public void deleteCustomer(@PathVariable(value = "customerId") String customerId) {
        Customer customer = verifyCustomer(customerId);
        customerRepository.delete(customer);
    }

   
    
    
    private BankAccount verifyBankAccount(String accountId) throws NoSuchElementException {
        return bankAccountRepository.findById(accountId).orElseThrow(() ->
                new NoSuchElementException("bank account does not exist"));
    }

    /**
     * Verify and return the Customer given a customerId.
     *
     * @param customerId customer identifier
     * @throws NoSuchElementException if no customer found.
     */
    private Customer verifyCustomer(String customerId) throws NoSuchElementException {
        return customerRepository.findById(customerId).orElseThrow(() ->
            new NoSuchElementException("Customer does not exist " + customerId));
        }
    

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex exception
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }

}
