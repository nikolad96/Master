package com.example.paypalservice.service;


import com.example.paypalservice.model.PaypalTransaction;
import com.example.paypalservice.repositorium.TransactionRepositorium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    TransactionRepositorium transactionRepositorium;

    public PaypalTransaction findOneByPaymentId(String id){
        return transactionRepositorium.findOneByPaymentId(id);
    }
}
