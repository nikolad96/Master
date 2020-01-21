package com.example.naucnacentrala.service;

import com.example.naucnacentrala.model.Casopis;
import com.example.naucnacentrala.repository.CasopisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CasopisService {

    @Autowired
    private CasopisRepository casopisRepository;

    public Casopis findOneById(Integer id){
        return casopisRepository.findOneById(id);
    }

    public List<Casopis> findAll(){
        return casopisRepository.findAll();
    }

    public Casopis save(Casopis casopis){
        return casopisRepository.save(casopis);
    }

    public void remove(Integer id){
        casopisRepository.deleteById(id);
    }

    public Casopis findOneByName(String naziv){
        return casopisRepository.findOneByNaziv(naziv);
    }
}
