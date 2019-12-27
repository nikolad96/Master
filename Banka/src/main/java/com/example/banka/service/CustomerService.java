package com.example.banka.service;

import com.example.banka.model.Customer;
import com.example.banka.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer findOneById(Integer id){ return customerRepository.findOneById(id); }
    public List<Customer> findAll(){
        return customerRepository.findAll();
    }
    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }
    public Customer findOneByMerchantId(String id) { return customerRepository.findOneByMerchantId(id); }
}
