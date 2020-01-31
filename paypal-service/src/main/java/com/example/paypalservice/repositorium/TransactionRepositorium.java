package com.example.paypalservice.repositorium;

import com.example.paypalservice.model.PaypalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepositorium extends JpaRepository<PaypalTransaction, Integer> {

    List<PaypalTransaction> findAll();
    PaypalTransaction findOneById(Integer Id);
    PaypalTransaction findOneByPaymentId(String Id);


}
