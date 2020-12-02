package com.example.bankapp;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.bankapp.service.BankAccountService;
import com.example.bankapp.service.CustomerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
// author Meyappan Ramasamy
@SpringBootApplication
public class BankingApplication implements CommandLineRunner {

    @Value("${bankapp.importfile}")
    private String importFile;
    
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BankAccountService bankAccountService;

    public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        createCustomers(importFile);
        createBankAccounts();
    }

    /**
     * Initialize all the known customers
     * @throws IOException 
     */
    private void createCustomers(String fileToImport) throws IOException{
    	
    	// manually create few customers using command line runner method
        customerService.createCustomer("Ram", "Sarjapur road Banagalore", "9972815573", "ram@gmail.com","male", "05-09-1981", "individual");
        
        customerService.createCustomer("Anand", "TNagar Chennai", "9123415674", "anand@gmail.com", "male", "05-06-1985", "organization");
        
        CustomerFromFile.read(fileToImport).forEach(customerFromFile -> 
        customerService.createCustomer(customerFromFile.getName(), customerFromFile.getAddress(), customerFromFile.getPhoneNumber(), customerFromFile.getEmailAddress(), customerFromFile.getGender(), customerFromFile.getDateOfBirth(), customerFromFile.getCustomerType()));
    
    }

    /**
     * manually create few bank accounts 
     */
    private void createBankAccounts() throws IOException {
       
        bankAccountService.createBankAccount("9972815573", 10000);
        bankAccountService.createBankAccount("9123415674", 20000);
      
    }

    /**
     * Helper class to import BankCustomers.json for a MongoDb Document.
     */
    private static class CustomerFromFile {
        //fields
        String name, address, phoneNumber, emailAddress, dateOfBirth, customerType, gender;
        
        //Map<String, String> details;

        CustomerFromFile(Map<String, String> record) {
        	System.out.println("customer record read from json file : " + record.toString());
            this.name =  record.get("name");
            this.address = record.get("address");
            this.phoneNumber = record.get("phoneNumber");
            this.emailAddress = record.get("emailAddress");
            this.dateOfBirth = record.get("dateOfBirth");
            this.customerType = record.get("customerType");
            this.gender = record.get("gender");
          
        }
        //reader
        static List<CustomerFromFile> read(String fileToImport) throws IOException {
            List<Map<String, String>> records = new ObjectMapper().setVisibility(FIELD, ANY).
                    readValue(new FileInputStream(fileToImport),
                            new TypeReference<List<Map<String, String>>>() {});
            return records.stream().map(CustomerFromFile::new)
                    .collect(Collectors.toList());
        }
		public String getName() {
			return name;
		}
		public String getAddress() {
			return address;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public String getEmailAddress() {
			return emailAddress;
		}
		public String getDateOfBirth() {
			return dateOfBirth;
		}
		public String getCustomerType() {
			return customerType;
		}
		public String getGender() {
			return gender;
		}
      
    }
}
