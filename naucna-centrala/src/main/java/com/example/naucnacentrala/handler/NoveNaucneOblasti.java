package com.example.naucnacentrala.handler;

import com.example.naucnacentrala.dto.FormSubmissionDto;
import com.example.naucnacentrala.model.Casopis;
import com.example.naucnacentrala.model.NaucnaOblast;
import com.example.naucnacentrala.service.CasopisService;
import com.example.naucnacentrala.service.NaucnaOblastService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoveNaucneOblasti implements JavaDelegate {

    @Autowired
    private CasopisService casopisService;

    @Autowired
    private NaucnaOblastService naucnaOblastService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("usao u NoveNaucneOblasti");

        Integer casopisId = (Integer) delegateExecution.getVariable("casopisId");
        Casopis casopis = casopisService.findOneById(casopisId);
        List<FormSubmissionDto> ispravkaDto = (List<FormSubmissionDto>) delegateExecution.getVariable("ispravkaNaucneOblasti");

        List<NaucnaOblast> noveOblasti = new ArrayList<>();

        for(FormSubmissionDto dto: ispravkaDto){
            if(dto.getFieldId().equals("naucna_oblast")){
                NaucnaOblast naucnaOblast;
                naucnaOblast = naucnaOblastService.findOneByNaziv(dto.getFieldValue());
                try{
                    naucnaOblast.getId();
                }catch(NullPointerException e){
                    naucnaOblast = new NaucnaOblast(dto.getFieldValue());
                }
                noveOblasti.add(naucnaOblast);
            }
        }

        casopis.setNaucneOblasti(noveOblasti);
        casopis = casopisService.save(casopis);

        System.out.println("izasao iz NoveNaucneOblasti");

    }
}
