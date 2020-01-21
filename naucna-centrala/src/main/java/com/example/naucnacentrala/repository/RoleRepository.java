package com.example.naucnacentrala.repository;

import com.example.naucnacentrala.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findOneById(Integer id);
    Role findOneByName(String name);
}
