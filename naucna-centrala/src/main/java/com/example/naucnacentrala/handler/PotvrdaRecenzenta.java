package com.example.naucnacentrala.handler;

import com.example.naucnacentrala.model.Korisnik;
import com.example.naucnacentrala.model.Recenzent;
import com.example.naucnacentrala.model.Role;
import com.example.naucnacentrala.service.KorisnikService;
import com.example.naucnacentrala.service.RoleService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PotvrdaRecenzenta implements JavaDelegate {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private RoleService roleService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("Usao u PotvrdaRecenzenta");

        String username = (String) delegateExecution.getVariable("username");
        System.out.println("username: " + username);

        try{
            Korisnik pronadjen = korisnikService.findOneByUsername(username);

            Role role = roleService.findOneByName("ROLE_RECENZENT");
//            pronadjen.getRoles().add(role);
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            pronadjen.setRoles(roles);
            pronadjen.setRecenzent(Recenzent.ODOBREN);

            pronadjen = korisnikService.save(pronadjen);

            System.out.println("Korisnik uspesno potvrdjen kao recenzent");
        }catch(NullPointerException e){
            System.out.println("Nema ovog korisnika");
        }







    }
}
