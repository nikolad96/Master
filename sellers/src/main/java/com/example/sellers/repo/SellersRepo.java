package com.example.sellers.repo;

import com.example.sellers.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellersRepo extends JpaRepository<Seller, String> {

    List<Seller> findAll();

    Seller findOneByName(String name);
    Seller findOneByPib(String pib);
    Seller findOneById(Integer id);
}
