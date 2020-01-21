package com.example.naucnacentrala.repository;

import com.example.naucnacentrala.model.Casopis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasopisRepository extends JpaRepository<Casopis, Integer> {

    Casopis findOneByNaziv(String naziv);
    Casopis findOneById(Integer id);
}
