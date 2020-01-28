package com.example.naucnacentrala.service;

import com.example.naucnacentrala.model.Kupovina;
import com.example.naucnacentrala.repository.KupovinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KupovinaService {

    @Autowired
    private KupovinaRepository kupovinaRepository;

    public Kupovina findOneById(Integer id){
        return kupovinaRepository.findOneById(id);
    }

    public List<Kupovina> findAll(){
        return kupovinaRepository.findAll();
    }

    public Kupovina save(Kupovina kupovina){
        return kupovinaRepository.save(kupovina);
    }

    public Kupovina findOneByPaymentId(Integer paymentId){ return kupovinaRepository.findOneByPaymentId(paymentId); }
}
