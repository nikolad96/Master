package com.example.paypalservice.repositorium;

import com.example.paypalservice.model.PaypalMerchant;
import com.example.paypalservice.model.PaypalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepositorium extends JpaRepository<PaypalMerchant, Integer> {

    PaypalMerchant findOneBySellerId(Integer Id);
}
