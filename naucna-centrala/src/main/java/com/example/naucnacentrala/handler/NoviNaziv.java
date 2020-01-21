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
public class NoviNaziv implements JavaDelegate {

    @Autowired
    private CasopisService casopisService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("usao u NoviNaziv");

        Integer casopisId = (Integer) delegateExecution.getVariable("casopisId");
        Casopis casopis = casopisService.findOneById(casopisId);
        List<FormSubmissionDto> ispravkaDto = (List<FormSubmissionDto>) delegateExecution.getVariable("ispravkaNaziv");

        for(FormSubmissionDto dto: ispravkaDto){
            if(dto.getFieldId().equals("naziv")){
                casopis.setNaziv(dto.getFieldValue());
            }
        }

        casopis = casopisService.save(casopis);

        System.out.println("izasao iz NoviNaziv");

    }
}
