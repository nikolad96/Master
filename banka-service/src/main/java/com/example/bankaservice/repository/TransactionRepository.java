package com.example.bankaservice.repository;

import com.example.bankaservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findOneById(Integer id);
}
