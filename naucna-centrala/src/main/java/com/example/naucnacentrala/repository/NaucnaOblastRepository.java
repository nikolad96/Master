package com.example.naucnacentrala.repository;

import com.example.naucnacentrala.model.NaucnaOblast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NaucnaOblastRepository extends JpaRepository<NaucnaOblast, Integer> {

    NaucnaOblast findOneById(Integer id);
    NaucnaOblast findOneByNaziv(String naziv);
}
