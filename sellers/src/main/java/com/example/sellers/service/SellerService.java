package com.example.sellers.service;

import com.example.sellers.model.Seller;
import com.example.sellers.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;


    public Seller findOneById(Integer id){
        return sellerRepository.findOneById(id);
    }

    public List<Seller> findAll(){
        return sellerRepository.findAll();
    }

    public Seller save(Seller seller){
        return sellerRepository.save(seller);
    }

    public Seller findOneByName(String name){
        return sellerRepository.findOneByName(name);
    }

    public Seller findOneBySellerId(Integer id){
        return sellerRepository.findOneBySellerId(id);
    }
}
