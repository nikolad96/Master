package com.example.naucnacentrala.controller;

import com.example.naucnacentrala.dto.CasopisDTO;
import com.example.naucnacentrala.dto.PaymentMethodsDTO;
import com.example.naucnacentrala.dto.ProveraClanarineDTO;
import com.example.naucnacentrala.dto.RadDTO;
import com.example.naucnacentrala.model.*;
import com.example.naucnacentrala.repository.CasopisRepository;
import com.example.naucnacentrala.repository.KorisnikRepository;
import com.example.naucnacentrala.security.TokenUtils;
import com.example.naucnacentrala.service.RadService;
import com.example.naucnacentrala.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    public TokenUtils tokenUtils;

    @Autowired
    private RadService radService;

    @RequestMapping(value = "/allMagazines", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<CasopisDTO> izlistajSve(HttpServletRequest request){

        String username = Utils.getUsernameFromRequest(request, tokenUtils);
        System.out.println("username: " + username);

        Korisnik korisnik = korisnikRepository.findOneByUsername(username);

        List<Casopis> casopisi = casopisRepository.findAll();
        List<CasopisDTO> payload = new ArrayList<>();

        for(Casopis c : casopisi){
            if(c.getAktiviran() == true) {
                CasopisDTO dto = new CasopisDTO();
                dto.setId(c.getId());
                dto.setIssn(c.getIssn());
                dto.setName(c.getNaziv());
                dto.setNaplacujeClanarina(c.getNaplataClanarine());
                List<RadDTO> radovi = new ArrayList<>();
                for(Rad rad: c.getRadovi()){
                    RadDTO radDto = new RadDTO(rad.getId(), rad.getNaslov(), rad.getApstrakt(), rad.getPdfLokacija(), rad.isPrihvacen(), rad.getKljucniPojmovi());
                    boolean kupljen = false;

                    if(c.getNaplataClanarine().equals(NaplacujeClanarina.NAPLATA_AUTORIMA)){
                        System.out.println("naplata autorima: " + c.getId());
                        kupljen = true;
                    }

                    for(Korisnik k : c.getKorisniciPlatili()){
                        if(korisnik.getId().equals(k.getId())){
                            System.out.println("kupljen casopis: " + c.getId());
                            kupljen = true;
                        }
                    }

                    for(Korisnik k : rad.getKorisniciPlatili()){
                        if(korisnik.getId().equals(k.getId())){
                            System.out.println("kupljen rad: " + rad.getId());
                            kupljen = true;
                        }
                    }
                    radDto.setKupljen(kupljen);
                    radovi.add(radDto);
                }
                dto.setRadovi(radovi);
                payload.add(dto);
            }
        }

        for(CasopisDTO ccc : payload){
            System.out.println(ccc.getId());
            System.out.println(ccc.getName());
            System.out.println(ccc.getIssn());
        }

        return payload;
    }

//    @RequestMapping(value = "/checkPaid", method = RequestMethod.POST, produces = "application/json")
//    public @ResponseBody Boolean proveriClanarinu(@RequestBody ProveraClanarineDTO dto @PathVariable("id") String id){
//        Korisnik korisnik = korisnikRepository.findOneById(dto.getId_korisnika());
//        Casopis casopis = casopisRepository.findOneById(dto.getId_casopisa());
//
//        for(Korisnik k : casopis.getKorisniciPlatili()){
//            if(korisnik.getId().equals(k.getId())){
//                return true;
//            }
//        }
//
//        return false;
//    }

    @RequestMapping(value = "/checkPaid/{radId}/{casopisId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Boolean proveriClanarinu(HttpServletRequest request, @PathVariable("radId") Integer radId, @PathVariable("casopisId") Integer casopisId){

        String username = Utils.getUsernameFromRequest(request, tokenUtils);
        System.out.println("username: " + username);

        Korisnik korisnik = korisnikRepository.findOneByUsername(username);
        Casopis casopis = casopisRepository.findOneById(casopisId);
        Rad rad = radService.findOneById(radId);

        if(casopis.getNaplataClanarine().equals(NaplacujeClanarina.NAPLATA_AUTORIMA)){
            System.out.println("naplata autorima: " + casopisId);
            return true;
        }

        for(Korisnik k : casopis.getKorisniciPlatili()){
            if(korisnik.getId().equals(k.getId())){
                System.out.println("kupljen casopis: " + casopisId);
                return true;
            }
        }

        for(Korisnik k : rad.getKorisniciPlatili()){
            if(korisnik.getId().equals(k.getId())){
                System.out.println("kupljen rad: " + radId);
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
