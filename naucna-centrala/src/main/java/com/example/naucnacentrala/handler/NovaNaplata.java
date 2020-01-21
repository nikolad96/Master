package com.example.naucnacentrala.handler;

import com.example.naucnacentrala.dto.FormSubmissionDto;
import com.example.naucnacentrala.model.Casopis;
import com.example.naucnacentrala.service.CasopisService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NovaNaplata implements JavaDelegate {

    @Autowired
    private CasopisService casopisService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("usao u NovaNaplata");

        Integer casopisId = (Integer) delegateExecution.getVariable("casopisId");
        Casopis casopis = casopisService.findOneById(casopisId);
        List<FormSubmissionDto> ispravkaDto = (List<FormSubmissionDto>) delegateExecution.getVariable("ispravkaNaplata");

        for(FormSubmissionDto dto: ispravkaDto){
            if(dto.getFieldId().equals("naplata_clanarine")){
                casopis.setNaplataClanarine(dto.getFieldValue());
            }
        }

        casopis = casopisService.save(casopis);

        System.out.println("izasao iz NovaNaplata");

    }
}
