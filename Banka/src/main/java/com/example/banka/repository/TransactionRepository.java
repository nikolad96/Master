package com.example.banka.repository;

import com.example.banka.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findOneById(Integer id);
    Transaction findOneByMerchantOrderId(Integer id);
}
