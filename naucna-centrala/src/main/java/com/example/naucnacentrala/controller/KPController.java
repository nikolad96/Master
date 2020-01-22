package com.example.naucnacentrala.controller;

import com.example.naucnacentrala.dto.CasopisDTO;
import com.example.naucnacentrala.dto.PaymentMethodsDTO;
import com.example.naucnacentrala.dto.ProveraClanarineDTO;
import com.example.naucnacentrala.model.Casopis;
import com.example.naucnacentrala.model.Korisnik;
import com.example.naucnacentrala.model.PaymentMethod;
import com.example.naucnacentrala.repository.CasopisRepository;
import com.example.naucnacentrala.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "KP")
public class KPController {

    @Autowired
    CasopisRepository casopisRepository;

    @Autowired
    KorisnikRepository korisnikRepository;

    @Autowired
    RestTemplate restTemplate;


    @RequestMapping(value = "/allMagazines", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody List<CasopisDTO> izlistajSve(){
        List<Casopis> casopisi = casopisRepository.findAll();
        List<CasopisDTO> payload = new ArrayList<>();
        for(Casopis c : casopisi){
            CasopisDTO dto = new CasopisDTO();
            dto.setId(c.getId());
            dto.setIssn(c.getIssn());
            dto.setName(c.getNaziv());
            dto.setNaplacujeClanarina(c.getNaplataClanarine());
            dto.setRadovi(c.getRadovi());
            payload.add(dto);
        }

        return payload;
    }

    @RequestMapping(value = "/checkPaid", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Boolean proveriClanarinu(@RequestBody ProveraClanarineDTO dto){
        Korisnik korisnik = korisnikRepository.findOneById(dto.getId_korisnika());
        Casopis casopis = casopisRepository.findOneById(dto.getId_casopisa());

        for(Korisnik k : casopis.getKorisniciPlatili()){
            if(korisnik.getId().equals(k.getId())){
                return true;
            }
        }

        return false;
    }

    @RequestMapping(value = "/getPaymentMethods", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody List<PaymentMethod> getPaymentMethods(@RequestBody CasopisDTO dto){
        Casopis c = casopisRepository.findOneById(dto.getId());

        PaymentMethodsDTO request = new PaymentMethodsDTO();
        request.setId(c.getId());

        ResponseEntity<PaymentMethodsDTO> response = restTemplate.postForEntity("http://localhost:8091/sellers/getPaymentMethods", request, PaymentMethodsDTO.class);
        List<PaymentMethod> lista = new ArrayList<>();

        for(PaymentMethod method : response.getBody().getPaymentMethods()){
            lista.add(method);
        }

        return lista;
    }

}
