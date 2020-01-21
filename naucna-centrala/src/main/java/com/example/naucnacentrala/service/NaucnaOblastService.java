package com.example.naucnacentrala.service;

import com.example.naucnacentrala.model.NaucnaOblast;
import com.example.naucnacentrala.repository.NaucnaOblastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NaucnaOblastService {

    @Autowired
    private NaucnaOblastRepository naucnaOblastRepository;

    public NaucnaOblast findOneById(Integer id){
        return naucnaOblastRepository.findOneById(id);
    }

    public List<NaucnaOblast> findAll(){
        return naucnaOblastRepository.findAll();
    }

    public NaucnaOblast save(NaucnaOblast naucnaOblast){
        return naucnaOblastRepository.save(naucnaOblast);
    }

    public void remove(Integer id){
        naucnaOblastRepository.deleteById(id);
    }

    public NaucnaOblast findOneByNaziv(String naziv){
        return naucnaOblastRepository.findOneByNaziv(naziv);
    }
}
