package com.example.paymentcardcenter.repository;

import com.example.paymentcardcenter.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findOneById(Integer id);
}
