package com.example.sellers.repository;

import com.example.sellers.model.SellerPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerPaymentRepository extends JpaRepository<SellerPayment, Integer> {

    SellerPayment findOneById(Integer id);
}
