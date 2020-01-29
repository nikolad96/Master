package com.example.sellers.service;

import com.example.sellers.model.SellerPayment;
import com.example.sellers.repository.SellerPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerPaymentService {

    @Autowired
    private SellerPaymentRepository sellerPaymentRepository;

    public SellerPayment findOneById(Integer id){
        return sellerPaymentRepository.findOneById(id);
    }

    public List<SellerPayment> findAll(){
        return sellerPaymentRepository.findAll();
    }

    public SellerPayment save(SellerPayment sellerPayment){
        return sellerPaymentRepository.save(sellerPayment);
    }
}
