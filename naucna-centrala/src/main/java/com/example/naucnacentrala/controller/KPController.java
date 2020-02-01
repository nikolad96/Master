package com.example.naucnacentrala.controller;

import com.example.naucnacentrala.dto.*;
import com.example.naucnacentrala.model.*;
import com.example.naucnacentrala.repository.CasopisRepository;
import com.example.naucnacentrala.repository.KorisnikRepository;
import com.example.naucnacentrala.repository.SubscriptionRepositorium;
import com.example.naucnacentrala.security.TokenUtils;
import com.example.naucnacentrala.service.*;
import com.example.naucnacentrala.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
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
    private SubscriptionRepositorium subscriptionRepositorium;

    @Autowired
    private KupovinaService kupovinaService;

    @Autowired
    private NaucnaOblastService naucnaOblastService;

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
                    for(Subscription s : c.getPretplate()){
                        if(s.getUsername().equals(korisnik.getUsername())) {
                            System.out.println("Pretplacen na casopis:"+ c.getNaziv());
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
    public @ResponseBody ResponseEntity<List<PaymentMethodDTO>> getPaymentMethodsCasopis(@PathVariable("casopisId") Integer casopisId){
        System.out.println("usao u getPaymentMethodsCasopis");

        ResponseEntity<PaymentMethodListDTO> response = restTemplate.getForEntity("https://localhost:8091/sellers/getPaymentMethods/" + casopisId, PaymentMethodListDTO.class);
        List<PaymentMethodDTO> methods = response.getBody().getMethods();

        return new ResponseEntity<List<PaymentMethodDTO>>(methods, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllPaymentMethods", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<List<PaymentMethodDTO>> getPaymentMethods(){
        System.out.println("usao u getPaymentMethods");

        ResponseEntity<PaymentMethodListDTO> response = restTemplate.getForEntity("https://localhost:8091/sellers/getAllPaymentMethods", PaymentMethodListDTO.class);
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
        ResponseEntity<PaymentResponseDTO> paymentResponseDTO = restTemplate.postForEntity("https://localhost:8086/banka-service/bankservice/payment", httpRequest, PaymentResponseDTO.class);

        System.out.println("return");

        Kupovina kupovina = new Kupovina(radId, paymentResponseDTO.getBody().getPaymentId(), korisnik);
        kupovina = kupovinaService.save(kupovina);

        return new ResponseEntity<PaymentResponseDTO>(new PaymentResponseDTO(paymentResponseDTO.getBody().getPaymentUrl(), paymentResponseDTO.getBody().getPaymentId()), HttpStatus.OK);

//        return restTemplate.postForEntity("https://localhost:8086/banka-service/bankservice/payment", httpRequest, PaymentResponseDTO.class);
    }

    @RequestMapping(value  = "/updateKupovina/{paymentId}", method = RequestMethod.GET)
    private ResponseEntity<String> updateKupovina(@PathVariable("paymentId") Integer paymentId){
        Kupovina kupovina = kupovinaService.findOneByPaymentId(paymentId);
        Rad rad = radService.findOneById(kupovina.getRadId());
        rad.getKorisniciPlatili().add(kupovina.getKorisnik());
        rad = radService.save(rad);
        return new ResponseEntity<>("[NC]: Kupovina update-ovana", HttpStatus.OK);
    }

    @RequestMapping(value  = "/paypalPaid/{radId}", method = RequestMethod.GET)
    private ResponseEntity<String> paypalPaid(@PathVariable("radId") Integer radId){
        System.out.println("USAO U PAYPAL PAID");
        Rad rad = radService.findOneById(radId);
        Korisnik korisnik = korisnikService.findOneByUsername("realmace");
        List<Korisnik> korisnici = rad.getKorisniciPlatili();
        korisnici.add(korisnik);
        rad.setKorisniciPlatili(korisnici);
        Rad r = radService.save(rad);
        return new ResponseEntity<>("[NC]: Kupovina update-ovana", HttpStatus.OK);
    }

    @RequestMapping(value = "/paymentBitcoin/{radId}/{casopisId}/{userId}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<PaymentResponseDTO> paymentBitcoin(@PathVariable("radId") Integer radId, @PathVariable("casopisId") Integer casopisId, @PathVariable("userId") String userId){
        PaymentBitcoinDTO request = new PaymentBitcoinDTO();
        Casopis c = casopisService.findOneById(casopisId);
        Rad r = radService.findOneById(radId);
        Korisnik k = korisnikService.findOneByUsername(userId);

        request.setAmount(r.getCena());
        request.setSeller_id(c.getId());
        request.setSeller_name(c.getNaziv());
        request.setTransaction_id(1);
        request.setBuyer_id(k.getId());
        request.setBuyer_name(k.getIme());
        request.setRad_id(radId);

        ResponseEntity<PaymentResponseDTO> paymentResponse = restTemplate.postForEntity("https://localhost:8090/bitcoin-service/transaction", request, PaymentResponseDTO.class);

        return paymentResponse;

    }

    @RequestMapping(value = "/paymentPaypal/{radId}/{casopisId}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<PaymentResponseDTO> paymentPaypal(HttpServletRequest request, @PathVariable("radId") Integer radId, @PathVariable("casopisId") Integer casopisId){
        PaymentPaypalDTO paymentPaypalDTO = new PaymentPaypalDTO();
        Casopis c = casopisService.findOneById(casopisId);
        Rad r = radService.findOneById(radId);
        String username = Utils.getUsernameFromRequest(request, tokenUtils);
        Korisnik k = korisnikService.findOneByUsername(username);



        paymentPaypalDTO.setAmount(r.getCena());
        paymentPaypalDTO.setSeller_id(c.getId());
        paymentPaypalDTO.setSeller_name(c.getNaziv());
        paymentPaypalDTO.setBuyer_id(k.getId());
        paymentPaypalDTO.setBuyer_name(k.getIme());
        paymentPaypalDTO.setRad_id(radId);

        ResponseEntity<PaymentResponseDTO> paymentResponse = restTemplate.postForEntity("https://localhost:8087/paypal/payment", paymentPaypalDTO, PaymentResponseDTO.class);

        return paymentResponse;

    }


    @RequestMapping(value = "/sendSubscription", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<SubscriptionDTO> getSubscriptions(@RequestBody SubscriptionDTO subscriptionDTO){
        PaymentPaypalDTO paymentPaypalDTO = new PaymentPaypalDTO();

        Subscription s = new Subscription();
        s.setDescription(subscriptionDTO.getDescription());
        s.setEndDate(subscriptionDTO.getEndDate());
        s.setId(subscriptionDTO.getId());
        s.setStartDate(subscriptionDTO.getStartDate());
        s.setSellerId(subscriptionDTO.getSellerId());
        s.setUsername(subscriptionDTO.getUsername());
        s.setState(subscriptionDTO.getState());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("U CONTROLLERU JE");
        System.out.println(username);
        List<Subscription> pretplate = new ArrayList<>();
        s.setUsername("realmace");
        Casopis c = casopisService.findOneById(subscriptionDTO.getSellerId());

        s.setCasopis(c);
        pretplate = c.getPretplate();
        pretplate.add(s);
        c.setPretplate(pretplate);
        casopisService.save(c);
        subscriptionRepositorium.save(s);





        return new ResponseEntity<SubscriptionDTO>(subscriptionDTO , HttpStatus.OK);

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

    @RequestMapping(value  = "/noviCasopis/{casopisId}/{nacinPlacanjaId}", method = RequestMethod.GET)
    private @ResponseBody ResponseEntity<CustomerResponseDTO> noviCasopis(@PathVariable("casopisId") Integer casopisId, @PathVariable("nacinPlacanjaId") Integer nacinPlacanjaId){

        System.out.println("Usao u noviCasopis");
        System.out.println("casopisId: " + casopisId + "; nacinPlacanjaId: " + nacinPlacanjaId);

        Casopis casopis = casopisService.findOneById(casopisId);

        if(!casopis.getAktiviran()) {
            casopis.setAktiviran(true);

            Rad rad1 = new Rad("Rad prvi", "apstrakt1", 80, naucnaOblastService.findOneById(1), "lokacija", true);
            rad1.setCasopis(casopis);
            rad1 = radService.save(rad1);
            casopis.getRadovi().add(rad1);
            casopis = casopisService.save(casopis);

            rad1 = new Rad("Rad drugi", "apstrakt1", 75, naucnaOblastService.findOneById(1), "lokacija", true);
            rad1.setCasopis(casopis);
            rad1 = radService.save(rad1);
            casopis.getRadovi().add(rad1);
            casopis = casopisService.save(casopis);

            rad1 = new Rad("Rad treci", "apstrakt1", 115, naucnaOblastService.findOneById(1), "lokacija", true);
            rad1.setCasopis(casopis);
            rad1 = radService.save(rad1);
            casopis.getRadovi().add(rad1);
            casopis = casopisService.save(casopis);
        }

        NewSellerDTO newSellerDTO = new NewSellerDTO(casopisId, nacinPlacanjaId, casopis.getNaziv());

        return restTemplate.postForEntity("https://localhost:8091/sellers/newSellerPayment", newSellerDTO, CustomerResponseDTO.class);

    }

}
