package com.example.sellers.repository;

import com.example.sellers.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {
    List<PaymentMethod> findAll();

    PaymentMethod findOneByName(String name);
    PaymentMethod findOneById(Integer id);

}
