package com.example.naucnacentrala.controller;

import com.example.naucnacentrala.dto.*;
import com.example.naucnacentrala.model.*;
import com.example.naucnacentrala.repository.CasopisRepository;
import com.example.naucnacentrala.repository.KorisnikRepository;
import com.example.naucnacentrala.security.TokenUtils;
import com.example.naucnacentrala.service.CasopisService;
import com.example.naucnacentrala.service.KorisnikService;
import com.example.naucnacentrala.service.KupovinaService;
import com.example.naucnacentrala.service.RadService;
import com.example.naucnacentrala.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
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
    private CasopisService casopisService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public TokenUtils tokenUtils;

    @Autowired
    private RadService radService;

    @Autowired
    private KupovinaService kupovinaService;

    @RequestMapping(value = "/allMagazines", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<CasopisDTO> izlistajSve(HttpServletRequest request){

        String username = Utils.getUsernameFromRequest(request, tokenUtils);
        System.out.println("username: " + username);

        Korisnik korisnik = korisnikService.findOneByUsername(username);

        List<Casopis> casopisi = casopisService.findAll();
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
                            break;
                        }
                    }

                    for(Korisnik k : rad.getKorisniciPlatili()){
                        if(korisnik.getId().equals(k.getId())){
                            System.out.println("kupljen rad: " + rad.getId());
                            kupljen = true;
                            break;
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

        Korisnik korisnik = korisnikService.findOneByUsername(username);
        Casopis casopis = casopisService.findOneById(casopisId);
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

    @RequestMapping(value = "/getPaymentMethods/{casopisId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<List<PaymentMethodDTO>> getPaymentMethods(@PathVariable("casopisId") Integer casopisId){
        System.out.println("usao u getPaymentMethods");

        ResponseEntity<PaymentMethodListDTO> response = restTemplate.getForEntity("http://localhost:8091/sellers/getPaymentMethods/" + casopisId, PaymentMethodListDTO.class);
        List<PaymentMethodDTO> methods = response.getBody().getMethods();

        return new ResponseEntity<List<PaymentMethodDTO>>(methods, HttpStatus.OK);
    }

    @RequestMapping(value = "/paymentBank/{radId}/{casopisId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<PaymentResponseDTO> paymentBank(HttpServletRequest request, @PathVariable("radId") Integer radId, @PathVariable("casopisId") Integer casopisId) {

        System.out.println("Usao u paymentBank");

        String username = Utils.getUsernameFromRequest(request, tokenUtils);
        System.out.println("username: " + username);
        Korisnik korisnik = korisnikService.findOneByUsername(username);

        System.out.println("radId: " + radId + "; casopisId: " + casopisId);

        Rad rad = radService.findOneById(radId);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(rad.getCena());
        paymentDTO.setSellerId(casopisId);

        HttpEntity<PaymentDTO> httpRequest = new HttpEntity<PaymentDTO>(paymentDTO);
        ResponseEntity<PaymentResponseDTO> paymentResponseDTO = restTemplate.postForEntity("http://localhost:8086/banka-service/bankservice/payment", httpRequest, PaymentResponseDTO.class);

        System.out.println("return");

        Kupovina kupovina = new Kupovina(radId, paymentResponseDTO.getBody().getPaymentId(), korisnik);
        kupovina = kupovinaService.save(kupovina);

        return new ResponseEntity<PaymentResponseDTO>(new PaymentResponseDTO(paymentResponseDTO.getBody().getPaymentUrl(), paymentResponseDTO.getBody().getPaymentId()), HttpStatus.OK);

//        return restTemplate.postForEntity("http://localhost:8086/banka-service/bankservice/payment", httpRequest, PaymentResponseDTO.class);
    }

    @RequestMapping(value  = "/updateKupovina/{paymentId}", method = RequestMethod.GET)
    private ResponseEntity<String> updateTransaction(@PathVariable("paymentId") Integer paymentId){
        Kupovina kupovina = kupovinaService.findOneByPaymentId(paymentId);
        Rad rad = radService.findOneById(kupovina.getRadId());
        rad.getKorisniciPlatili().add(kupovina.getKorisnik());
        rad = radService.save(rad);
        return new ResponseEntity<>("[NC]: Kupovina update-ovana", HttpStatus.OK);
    }

    @RequestMapping(value = "/paymentBitcoin/{radId}/{casopisId}/{userId}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<PaymentResponseDTO> paymentBitcoin(@PathVariable("radId") Integer radId, @PathVariable("casopisId") Integer casopisId, @PathVariable("userId") Integer userId){
        PaymentBitcoinDTO request = new PaymentBitcoinDTO();
        Casopis c = casopisService.findOneById(casopisId);
        Rad r = radService.findOneById(radId);
        Korisnik k = korisnikService.findOneById(userId);

        request.setAmount(r.getCena());
        request.setSeller_id(c.getId());
        request.setSeller_name(c.getNaziv());
        request.setTransaction_id(1);
        request.setBuyer_id(k.getId());
        request.setBuyer_name(k.getIme());
        request.setRad_id(radId);

        ResponseEntity<PaymentResponseDTO> paymentResponse = restTemplate.postForEntity("http://localhost:8090/bitcoin-service/transaction", request, PaymentResponseDTO.class);

        return paymentResponse;

    }

    @RequestMapping(value = "/paid/{radId}/{casopisId}/{userId}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> paid(@PathVariable("radId") Integer radId, @PathVariable("casopisId") Integer casopisId, @PathVariable("userId") Integer userId, @RequestBody StatusDTO dto){
        Casopis c = casopisService.findOneById(casopisId);
        Rad r = radService.findOneById(radId);
        Korisnik k = korisnikService.findOneById(userId);

        switch(dto.getStatus()){
            case "paid":
                List<Korisnik> korisnici = r.getKorisniciPlatili();

                korisnici.add(k);

                r.setKorisniciPlatili(korisnici);

                return new ResponseEntity<>(HttpStatus.OK);
            case "invalid":
                break;
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
