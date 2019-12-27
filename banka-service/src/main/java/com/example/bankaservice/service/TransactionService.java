package com.example.bankaservice.service;

import com.example.bankaservice.model.Transaction;
import com.example.bankaservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction findOneById(Integer id){ return transactionRepository.findOneById(id); }
    public List<Transaction> findAll(){
        return transactionRepository.findAll();
    }
    public Transaction save(Transaction transaction){
        return transactionRepository.save(transaction);
    }
}
