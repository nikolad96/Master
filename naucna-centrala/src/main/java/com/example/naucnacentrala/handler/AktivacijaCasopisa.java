package com.example.naucnacentrala.handler;

import com.example.naucnacentrala.model.Casopis;
import com.example.naucnacentrala.service.CasopisService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AktivacijaCasopisa implements JavaDelegate {

    @Autowired
    private CasopisService casopisService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("usao u AktivacijaCasopisa");

        Integer casopisId = (Integer) delegateExecution.getVariable("casopisId");
        Casopis casopis = casopisService.findOneById(casopisId);

        casopis.setAktiviran(true);

        casopis = casopisService.save(casopis);

        System.out.println("izasao iz AktivacijaCasopisa");

    }
}
