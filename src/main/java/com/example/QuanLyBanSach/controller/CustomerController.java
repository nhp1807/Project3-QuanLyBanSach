package com.example.QuanLyBanSach.controller;

import com.example.QuanLyBanSach.model.Account;
import com.example.QuanLyBanSach.model.Customer;
import com.example.QuanLyBanSach.model.Role;
import com.example.QuanLyBanSach.service.AccountService;
import com.example.QuanLyBanSach.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Create a new customer
    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        // Check if any field is empty
        if (customer.getName().isEmpty() || customer.getGender() == null || customer.getDob().isEmpty() || customer.getPhone().isEmpty() || customer.getAddress().isEmpty()){
            return new ResponseEntity("Please fill all fields", HttpStatus.BAD_REQUEST);
        }

        // Check if there is a customer with the same phone number
        if (customerService.getCustomerByPhone(customer.getPhone()) != null) {
            return new ResponseEntity("Customer already exists", HttpStatus.BAD_REQUEST);
        }

        Account account = new Account();
        account.setUsername(customer.getPhone());
        account.setPassword(encoder.encode("password"));
        account.setRole(Role.USER);
        accountService.addAccount(account);

        customer.setAccountId(account.getId());

        Customer newCustomer = customerService.addCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }

    // Update a customer
    @PostMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer){
        Customer existingCustomer = customerService.getCustomerById(customer.getId());
        // Check if the customer exists
        if (existingCustomer == null) {
            return new ResponseEntity("Customer not found", HttpStatus.NOT_FOUND);
        }

        existingCustomer.setId(customer.getId());
        existingCustomer.setName(customer.getName());
        existingCustomer.setGender(customer.getGender());
        existingCustomer.setDob(customer.getDob());
        existingCustomer.setPhone(existingCustomer.getPhone());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setAccountId(existingCustomer.getAccountId());

        customerService.updateCustomer(existingCustomer);

        return new ResponseEntity<>(existingCustomer, HttpStatus.OK);
    }

    // Delete a customer
    @DeleteMapping("/delete")
    public ResponseEntity<Customer> deleteCustomer(@RequestParam Long id){
        Customer existingCustomer = customerService.getCustomerById(id);
        // Check if the customer exists
        if (existingCustomer == null) {
            return new ResponseEntity("Customer not found", HttpStatus.NOT_FOUND);
        }

        customerService.deleteCustomer(id);

        return new ResponseEntity<>(existingCustomer, HttpStatus.OK);
    }

    // Get a customer by id
    @GetMapping("/get")
    public ResponseEntity<Customer> getCustomerById(@RequestParam Long id){
        Customer customer = customerService.getCustomerById(id);
        // Check if the customer exists
        if (customer == null) {
            return new ResponseEntity("Customer not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    // Get all customers
    @GetMapping("/get/all")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // Get a customer by phone
    @GetMapping("/get/phone")
    public ResponseEntity<Customer> getCustomerByPhone(@RequestParam String phone){
        Customer existingCustomer = customerService.getCustomerByPhone(phone);
        // Check if the customer exists
        if (existingCustomer == null) {
            return new ResponseEntity("Customer not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(existingCustomer, HttpStatus.OK);
    }

    // Get a customer by name
    @GetMapping("/get/name")
    public ResponseEntity<List<Customer>> getCustomerByName(@RequestParam String name){
        // Check if the customer exists
        if (customerService.getCustomerByName(name).isEmpty()) {
            return new ResponseEntity("Customer not found", HttpStatus.NOT_FOUND);
        }
        List<Customer> customers = customerService.getCustomerByName(name);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
