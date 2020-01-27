package com.example.sellers.service;

import com.example.sellers.model.PaymentMethod;
import com.example.sellers.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;


    public PaymentMethod findOneById(Integer id){
        return paymentMethodRepository.findOneById(id);
    }

    public List<PaymentMethod> findAll(){
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod save(PaymentMethod paymentMethod){
        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod findOneByName(String name){
        return paymentMethodRepository.findOneByName(name);
    }
}
