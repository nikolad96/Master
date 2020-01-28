package com.example.naucnacentrala.repository;

import com.example.naucnacentrala.model.Kupovina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KupovinaRepository extends JpaRepository<Kupovina, Integer> {

    Kupovina findOneById(Integer id);
    Kupovina findOneByPaymentId(Integer id);
}
