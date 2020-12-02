package com.example.bankapp.domain;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BankAccount {

    @Id
    private String accountId; // accountId can be considered as accountNumber
    
    private Double accountBalance;
    
    private Double interestRate;
    
    private String accountType;
    
    private Date openDate;
    
   // @NotNull
    @Indexed
    private String customerId;
    
    private Map<String,String> details;
    
    protected BankAccount() {}

    public BankAccount(String customerId, Double initialBalance) {
        super();
        
        this.accountBalance = initialBalance;
        this.customerId = customerId;
        this.accountType = "savings"; // can be savings account, salary account, loan account, reimbursement account etc. 
        this.openDate = Date.from(Instant.now());
        this.interestRate = 3.5; //default interest rate per annum
    }
    
    

    public BankAccount(String customerId, Double accountBalance, String accountType, Map<String,String> details) {
        super();
        this.accountBalance = accountBalance;
        this.accountType = accountType;
        this.customerId = customerId;
        this.details = details;
        this.openDate = Date.from(Instant.now());
        this.interestRate = 3.5;
    }



    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }



    public Map<String, String> getDetails() {
        return details;
    }



    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
    
    


	@Override
	public int hashCode() {
		return Objects.hash(accountBalance, accountId, accountType, customerId, details, interestRate, openDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		return Objects.equals(accountBalance, other.accountBalance) && Objects.equals(accountId, other.accountId)
				&& Objects.equals(accountType, other.accountType) && Objects.equals(customerId, other.customerId)
				&& Objects.equals(details, other.details) && Objects.equals(interestRate, other.interestRate)
				&& Objects.equals(openDate, other.openDate);
	}

	@Override
	public String toString() {
		return "BankAccount [accountId=" + accountId + ", accountBalance=" + accountBalance + ", interestRate="
				+ interestRate + ", accountType=" + accountType + ", openDate=" + openDate + ", customerId="
				+ customerId + ", details=" + details + "]";
	}

	
    
    
    
    
}   
    
    
    
    
    

