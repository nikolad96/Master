package com.example.naucnacentrala.service;

import com.example.naucnacentrala.model.Rad;
import com.example.naucnacentrala.repository.RadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RadService {

    @Autowired
    private RadRepository radRepository;

    public Rad findOneById(Integer id){ return radRepository.findOneById(id); }

    public List<Rad> findAll(){
        return radRepository.findAll();
    }

    public Rad save(Rad rad){
        return radRepository.save(rad);
    }
}
