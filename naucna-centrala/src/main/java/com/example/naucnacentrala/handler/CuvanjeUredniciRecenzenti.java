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

import java.util.List;

@Service
public class CuvanjeUredniciRecenzenti implements JavaDelegate {

    @Autowired
    private CasopisService casopisService;

    @Autowired
    private KorisnikService korisnikService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("usao u CuvanjeUredniciRecenzenti");

        Integer casopisId = (Integer) delegateExecution.getVariable("casopisId");
        Casopis casopis = casopisService.findOneById(casopisId);

        List<FormSubmissionDto> uredniciRecenzentiDto = (List<FormSubmissionDto>) delegateExecution.getVariable("uredniciRecenzentiDto");

        for(int i=0; i<uredniciRecenzentiDto.size(); i++){
            if(uredniciRecenzentiDto.get(i).getFieldId().equals("urednici")){
                if(!uredniciRecenzentiDto.get(i).getFieldValue().equals("")) {
                    Korisnik urednik = korisnikService.findOneByUsername(uredniciRecenzentiDto.get(i).getFieldValue());
                    casopis.getUrednici().add(urednik);
                }
            }else if(uredniciRecenzentiDto.get(i).getFieldId().equals("recenzenti")){
                Korisnik recenzent = korisnikService.findOneByUsername(uredniciRecenzentiDto.get(i).getFieldValue());
                casopis.getRecenzenti().add(recenzent);
            }
        }

        casopis = casopisService.save(casopis);

        System.out.println("izasao iz CuvanjeUredniciRecenzenti");

    }
}
