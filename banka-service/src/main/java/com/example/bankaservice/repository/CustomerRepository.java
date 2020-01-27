package com.example.bankaservice.repository;

import com.example.bankaservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findOneById(Integer id);
    Customer findOneByMerchantId(String id);
    Customer findOneBySellerId(Integer sellerId);
}
