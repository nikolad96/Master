package com.example.naucnacentrala.handler;

import com.example.naucnacentrala.dto.FormSubmissionDto;
import com.example.naucnacentrala.model.*;
import com.example.naucnacentrala.service.KorisnikService;
import com.example.naucnacentrala.service.NaucnaOblastService;
import com.example.naucnacentrala.service.RoleService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveraPodataka implements JavaDelegate {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private NaucnaOblastService naucnaOblastService;
//
//    @Autowired
//    public JavaMailSender emailSender;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("usao u Provera podataka");

        List<FormSubmissionDto> registration = (List<FormSubmissionDto>) delegateExecution.getVariable("registration");
        System.out.println("id procesa: " + delegateExecution.getProcessInstanceId());

        Korisnik korisnik = new Korisnik();
        String mail = "";

        for(int i=0; i<registration.size(); i++){
            if(registration.get(i).getFieldId().equals("ime")){
                korisnik.setIme(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("prezime")){
                korisnik.setPrezime(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("grad")){
                korisnik.setGrad(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("drzava")){
                korisnik.setDrzava(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("titula")){
                korisnik.setTitula(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("email")){
                mail = registration.get(i).getFieldValue();
                korisnik.setEmail(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("username")){
                korisnik.setUsername(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("password")){
                String salt = BCrypt.gensalt();
                String hashedPass = BCrypt.hashpw(registration.get(i).getFieldValue(), salt);
                korisnik.setPassword(hashedPass);
//                korisnik.setPassword(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("recenzent")){
                System.out.println("recenzent: " + registration.get(i).getFieldValue());

                Role role = roleService.findOneByName("ROLE_KORISNIK");
//                if(registration.get(i).getFieldValue() == null || registration.get(i).getFieldValue().equals(null)){
                if(registration.get(i).getFieldValue().equals(false) || registration.get(i).getFieldValue().equals("false")){
                    korisnik.setRecenzent(Recenzent.BEZ_ZAHTEVA);
                }else{
                    korisnik.setRecenzent(Recenzent.ZAHTEV);
                }
                korisnik.getRoles().add(role);
            }else if(registration.get(i).getFieldId().equals("naucna_oblast")){
                NaucnaOblast naucnaOblast;
                naucnaOblast = naucnaOblastService.findOneByNaziv(registration.get(i).getFieldValue());
                try{
                    naucnaOblast.getId();
                }catch(NullPointerException e){
                    naucnaOblast = new NaucnaOblast(registration.get(i).getFieldValue());
                }
                korisnik.addNaucnaOblast(naucnaOblast);
            }
        }

        delegateExecution.setVariable("registration", registration);

        korisnik.setAktiviran(false);
        korisnik = korisnikService.save(korisnik);

        System.out.println("izasao iz ProveraPodataka");

    }
}
