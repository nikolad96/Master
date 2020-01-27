package com.example.sellers.repository;

import com.example.sellers.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, String> {

    List<Seller> findAll();

    Seller findOneByName(String name);
    Seller findOneByPib(String pib);
    Seller findOneById(Integer id);
    Seller findOneBySellerId(Integer id);
}
