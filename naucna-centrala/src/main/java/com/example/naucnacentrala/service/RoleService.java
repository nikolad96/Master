package com.example.naucnacentrala.service;

import com.example.naucnacentrala.model.Role;
import com.example.naucnacentrala.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findOneById(Integer id){
        return roleRepository.findOneById(id);
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role save(Role role){
        return roleRepository.save(role);
    }

    public void remove(Integer id){
        roleRepository.deleteById(id);
    }

    public Role findOneByName(String name){
        return roleRepository.findOneByName(name);
    }
}
