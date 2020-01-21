package com.example.naucnacentrala.handler;

import com.example.naucnacentrala.dto.FormSubmissionDto;
import com.example.naucnacentrala.model.Korisnik;
import com.example.naucnacentrala.service.KorisnikService;
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
public class SlanjeMejla implements JavaDelegate {


    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private KorisnikService korisnikService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("usao u SlanjeMejla");
        String username = "";

        List<FormSubmissionDto> registration = (List<FormSubmissionDto>) delegateExecution.getVariable("registration");
        for(FormSubmissionDto dto: registration){
            if(dto.getFieldId().equals("username")){
                username = dto.getFieldValue();
            }
        }

        Korisnik korisnik = korisnikService.findOneByUsername(username);

        System.out.println("Sending Email to: " + korisnik.getEmail() + "...");

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            String salt = BCrypt.gensalt();
            String hash = BCrypt.hashpw(korisnik.getUsername(), salt);
            runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "hashVrednost", hash);


            message.setTo(korisnik.getEmail());
            message.setSubject("Potvrda registracije");

            String confirmationUrl
                    =  "http://localhost:4200/activation/" + korisnik.getId() + "/" + delegateExecution.getProcessInstanceId();
            message.setText("Postovani/a, " + korisnik.getIme() + " " + korisnik.getPrezime() + "\n\n" +
                    "Da biste potvrdili Vasu registraciju, kliknite na link ispod.\n "
                    + confirmationUrl + "\n\n Srdacno,\n Naucna centrala");
            emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Done");

        System.out.println("izasao iz SlanjeMejla");

    }
}
