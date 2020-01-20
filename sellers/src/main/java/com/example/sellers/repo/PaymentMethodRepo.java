package com.example.sellers.repo;

import com.example.sellers.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, String> {
    List<PaymentMethod> findAll();

    PaymentMethod findOneByName(String name);

}
