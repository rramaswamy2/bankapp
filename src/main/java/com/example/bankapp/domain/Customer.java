package com.example.bankapp.domain;



import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Customer {

    @Id
    private String customerId;

    private String name;
    
    private String address;  // this can further evolve to houseNumber, street, city, state, country etc. 
    
    private String phoneNumber;  
    
    private String emailAddress;
    
    private String dateOfBirth;
    
    private String customerType;  //individual or residential customer, or organization or SOHO business customer

    private String gender;  // male or female 
    
    private String organizationName;
    
    protected Customer() {}
    
    public Customer(String name, String address, String phoneNumber, String emailAddress, String gender, String dateOfBirth, String customerType) {
        super();
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.gender = gender;
        
        this.dateOfBirth = dateOfBirth;
        this.customerType = customerType;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerId() {
        return customerId;
    }

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(address, customerId, customerType, dateOfBirth, emailAddress, gender, name,
				organizationName, phoneNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(address, other.address) && Objects.equals(customerId, other.customerId)
				&& Objects.equals(customerType, other.customerType) && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(emailAddress, other.emailAddress) && Objects.equals(gender, other.gender)
				&& Objects.equals(name, other.name) && Objects.equals(organizationName, other.organizationName)
				&& Objects.equals(phoneNumber, other.phoneNumber);
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", address=" + address + ", phoneNumber="
				+ phoneNumber + ", emailAddress=" + emailAddress + ", dateOfBirth=" + dateOfBirth + ", customerType="
				+ customerType + ", gender=" + gender + ", organizationName=" + organizationName + "]";
	}

	
   
}
