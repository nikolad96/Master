package com.example.bitcoinservice.repo;

import com.example.bitcoinservice.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepo extends JpaRepository<Seller, String> {
    List<Seller> findAll();

    Seller findOneById(Integer id);
}
