package com.example.banka.repository;

import com.example.banka.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findOneById(Integer id);
    Customer findOneByMerchantId (String id);
}
