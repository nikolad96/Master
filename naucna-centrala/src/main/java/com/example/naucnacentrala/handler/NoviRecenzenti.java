package com.example.naucnacentrala.handler;

import com.example.naucnacentrala.dto.FormSubmissionDto;
import com.example.naucnacentrala.model.Casopis;
import com.example.naucnacentrala.model.Korisnik;
import com.example.naucnacentrala.service.CasopisService;
import com.example.naucnacentrala.service.KorisnikService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoviRecenzenti implements JavaDelegate {

    @Autowired
    private CasopisService casopisService;

    @Autowired
    private KorisnikService korisnikService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("usao u NoviRecenzenti");

        Integer casopisId = (Integer) delegateExecution.getVariable("casopisId");
        Casopis casopis = casopisService.findOneById(casopisId);
        List<FormSubmissionDto> ispravkaDto = (List<FormSubmissionDto>) delegateExecution.getVariable("ispravkaRecenzenti");

        List<Korisnik> noviRecenzenti = new ArrayList<>();

        for(FormSubmissionDto dto: ispravkaDto){
            if(dto.getFieldId().equals("recenzenti_novi")){
                Korisnik recenzent = korisnikService.findOneByUsername(dto.getFieldValue());
                noviRecenzenti.add(recenzent);
            }
        }

        casopis.setRecenzenti(noviRecenzenti);
        casopis = casopisService.save(casopis);

        System.out.println("izasao iz NoviRecenzenti");

    }
}
