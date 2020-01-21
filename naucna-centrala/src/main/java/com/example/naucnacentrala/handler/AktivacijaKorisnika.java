package com.example.naucnacentrala.handler;

import com.example.naucnacentrala.model.Korisnik;
import com.example.naucnacentrala.service.KorisnikService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AktivacijaKorisnika implements JavaDelegate {

    @Autowired
    private KorisnikService korisnikService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("usao u AktivacijaKorisnikaService");

//        String id = "";
//        Integer id_user = -5;
//        boolean pronadjen_id = false;
//
//        while(pronadjen_id == false) {
////            id = (String) runtimeService.getVariable(delegateExecution.getProcessInstanceId(), "id_user");
////            System.out.println("id: " + id);
////            id_user = Integer.parseInt(id);
////            boolean pronadjen = (boolean) delegateExecution.getVariable("potvrdjeno");
////            System.out.println("pronadjen: " + pronadjen);
//
////
////            id_user = (Integer) delegateExecution.getVariable("id_user");
////            System.out.println("proces id: " + delegateExecution.getProcessInstanceId() + "; id_user: " + id_user);
//
//            if(id_user == null){
//                Thread.sleep(10000);
//            }else{
//                pronadjen_id = true;
//            }
//
//        }


        String username = (String) delegateExecution.getVariable("username");
        System.out.println("username: " + username);

        Boolean recenzent = (Boolean) delegateExecution.getVariable("recenzent");
        System.out.println("recenzent: " + recenzent);

        Korisnik pronadjen = korisnikService.findOneByUsername(username);
        if(pronadjen == null || pronadjen.isAktiviran() == true) {
            System.out.println("Nema ovog korisnika, ili je vec aktiviran");
        }else{
            pronadjen.setAktiviran(true);
            korisnikService.save(pronadjen);
            //response.sendRedirect("http://localhost:4200");
            System.out.println("Korisnik je uspesno aktiviran.");

        }
    }
}
