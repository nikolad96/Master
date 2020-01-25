package com.example.naucnacentrala.handler;

import com.example.naucnacentrala.dto.FormSubmissionDto;
import com.example.naucnacentrala.model.Casopis;
import com.example.naucnacentrala.model.Korisnik;
import com.example.naucnacentrala.model.NaplacujeClanarina;
import com.example.naucnacentrala.model.NaucnaOblast;
import com.example.naucnacentrala.service.CasopisService;
import com.example.naucnacentrala.service.KorisnikService;
import com.example.naucnacentrala.service.NaucnaOblastService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuvanjePodatakaCasopis implements JavaDelegate {

    @Autowired
    private NaucnaOblastService naucnaOblastService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private CasopisService casopisService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("usao u CuvanjePodatakaCasopisService");

        List<FormSubmissionDto> casopisDto = (List<FormSubmissionDto>) delegateExecution.getVariable("casopis");

        Casopis casopis = new Casopis();

        for(int i=0; i<casopisDto.size(); i++){
            if(casopisDto.get(i).getFieldId().equals("naziv")){
                casopis.setNaziv(casopisDto.get(i).getFieldValue());
            }else if(casopisDto.get(i).getFieldId().equals("ISSN_broj")){
                casopis.setIssn(casopisDto.get(i).getFieldValue());
            }else if(casopisDto.get(i).getFieldId().equals("naplata_clanarine")){
                if(casopisDto.get(i).getFieldValue().equals("naplata_citaocima")) {
                    casopis.setNaplataClanarine(NaplacujeClanarina.NAPLATA_CITAOCIMA);
                }else{
                    casopis.setNaplataClanarine(NaplacujeClanarina.NAPLATA_AUTORIMA);
                }
            }else if(casopisDto.get(i).getFieldId().equals("naucna_oblast")){
                NaucnaOblast naucnaOblast;
                naucnaOblast = naucnaOblastService.findOneByNaziv(casopisDto.get(i).getFieldValue());
                try{
                    naucnaOblast.getId();
                }catch(NullPointerException e){
                    naucnaOblast = new NaucnaOblast(casopisDto.get(i).getFieldValue());
                }
                casopis.getNaucneOblasti().add(naucnaOblast);
            }
        }

        String username = (String) delegateExecution.getVariable("glavniUrednikUsername");
        System.out.println("Glavni urednik username: " + username);
        Korisnik glavniUrednik = korisnikService.findOneByUsername(username);

        casopis.setAktiviran(false);
        casopis.setGlavniUrednik(glavniUrednik);
        casopis = casopisService.save(casopis);

        System.out.println("id novog cassopisa: " + casopis.getId());
        delegateExecution.setVariable("casopisId", casopis.getId());

        System.out.println("izasao iz CuvanjePodatakaCasopisService");

    }
}
