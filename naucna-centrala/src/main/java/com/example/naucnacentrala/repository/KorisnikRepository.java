package com.example.naucnacentrala.repository;

import com.example.naucnacentrala.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {

    Korisnik findOneById(Integer id);
    Korisnik findOneByUsername(String username);
    Korisnik findOneByEmail(String email);
}
