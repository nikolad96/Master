package com.example.bitcoinservice.repo;

import com.example.bitcoinservice.model.Transaction;
import com.example.bitcoinservice.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, String> {
    List<Transaction> findAll();

    Transaction findOneById(Integer id);
    List<Transaction> findTransactionsBySellerId(Integer id);
    List<Transaction> findTransactionsByBuyerId(Integer id);
    List<Transaction> findTransactionsByTransactionStatus(TransactionStatus status);
}
