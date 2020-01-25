package com.example.naucnacentrala.repository;

import com.example.naucnacentrala.model.Rad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RadRepository extends JpaRepository<Rad, Integer> {

    Rad findOneById(Integer id);
}
