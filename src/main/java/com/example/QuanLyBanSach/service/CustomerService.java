package com.example.QuanLyBanSach.service;

import com.example.QuanLyBanSach.model.Customer;
import com.example.QuanLyBanSach.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }
    public Customer getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }
}
