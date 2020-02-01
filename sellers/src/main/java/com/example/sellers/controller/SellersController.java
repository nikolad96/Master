package com.example.sellers.controller;


import com.example.sellers.dto.*;
import com.example.sellers.model.PaymentMethod;
import com.example.sellers.model.Seller;
import com.example.sellers.model.SellerPayment;
import com.example.sellers.service.PaymentMethodService;
import com.example.sellers.service.SellerService;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/sellers")
public class SellersController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @RequestMapping(method = RequestMethod.GET, path="/getSellers", produces = "application/json")
    public @ResponseBody List<SellerDTO> getSellers(){
        List<Seller> sellers = sellerService.findAll();
        List<SellerDTO> payload = new ArrayList<>();

        for(Seller seller : sellers){
            SellerDTO dto = new SellerDTO();
            dto.setId(seller.getId());
            dto.setName(seller.getName());
            List<PaymentMethod> methods = new ArrayList<>();
            for(SellerPayment payment: seller.getPaymentMethods()){
                if(payment.isPotvrdjeno()) {
                    methods.add(payment.getPaymentMethod());
                }
            }
            dto.setPaymentMethods(methods);
            payload.add(dto);
        }

        return payload;
    }

    @RequestMapping(method = RequestMethod.GET, path="/getSeller/{sellerName}", produces = "application/json")
    public @ResponseBody SellerDTO getSeller(@PathVariable String sellerName){
        Seller seller = sellerService.findOneByName(sellerName);
        SellerDTO payload = new SellerDTO();
        payload.setId(seller.getId());
        payload.setName(seller.getName());
        List<PaymentMethod> methods = new ArrayList<>();
        for(SellerPayment payment: seller.getPaymentMethods()){
            if(payment.isPotvrdjeno()) {
                methods.add(payment.getPaymentMethod());
            }
        }
        payload.setPaymentMethods(methods);

        return payload;
    }

//    @RequestMapping(method = RequestMethod.POST, path="/putSeller", produces = "application/json")
//    public @ResponseBody ResponseEntity putSeller(@RequestBody SellerDTO dto) throws Exception{
//        Seller seller = new Seller();
//
//        seller.setSellerId(dto.getId());
//        seller.setName(dto.getName());
//        List<PaymentMethod> methods = new ArrayList<>();
//        for(Payment payment: dto.getPaymentMethods()){
//            methods.add(payment.getPaymentMethod());
//        }
//        seller.setPaymentMethods(dto.getPaymentMethods());
//
//        for(PaymentMethod pm : dto.getPaymentMethods()){
//            if(pm.getName().equals("bitcoin")){
//                SellerBtcDTO btcDto = new SellerBtcDTO();
//                btcDto.setId(dto.getId());
//                btcDto.setSecret(dto.getSecret());
//
//                ResponseEntity<SellerDTO> res = restTemplate.postForEntity("https://localhost:8090/bitcoin-service/newSeller", btcDto, SellerDTO.class);
//
//            }
//        }
//
//        try {
//            sellerService.save(seller);
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return new ResponseEntity(HttpStatus.OK);
//
//    }

    @RequestMapping(value = "/getPaymentMethods/{casopisId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<PaymentMethodListDTO> getPaymentMethodsCasopis(@PathVariable("casopisId") Integer casopisId){
        Seller seller = sellerService.findOneBySellerId(casopisId);
        List<PaymentMethodDTO> lista = new ArrayList<>();

        for(SellerPayment payment: seller.getPaymentMethods()){
            if(payment.isPotvrdjeno()) {
                PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO(payment.getPaymentMethod().getId(), payment.getPaymentMethod().getName());
                lista.add(paymentMethodDTO);
            }
        }

        PaymentMethodListDTO paymentMethodListDTO = new PaymentMethodListDTO();
        paymentMethodListDTO.setMethods(lista);

        return new ResponseEntity<PaymentMethodListDTO>(paymentMethodListDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllPaymentMethods", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<PaymentMethodListDTO> getPaymentMethods(){

        List<PaymentMethodDTO> naciniPlacanja = new ArrayList<>();

        List<PaymentMethod> methods = paymentMethodService.findAll();

        for(PaymentMethod method : methods){
            PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO(method.getId(), method.getName());
            naciniPlacanja.add(paymentMethodDTO);
        }

        PaymentMethodListDTO paymentMethodListDTO = new PaymentMethodListDTO();
        paymentMethodListDTO.setMethods(naciniPlacanja);

        return new ResponseEntity<PaymentMethodListDTO>(paymentMethodListDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path="/newSellerPayment", produces = "application/json")
    public @ResponseBody ResponseEntity<CustomerResponseDTO> newSeller(@RequestBody NewSellerDTO dto){

        System.out.println("Usao u newSellerPayment");
        System.out.println("sellerId: " + dto.getSellerId() + "; name: " + dto.getName() + "; paymentMethodId: " + dto.getPaymentMethodId());

        Seller seller = sellerService.findOneBySellerId(dto.getSellerId());

        try{
            seller.getId();
        }catch (Exception e){
            seller = new Seller(dto.getSellerId(), dto.getName());
            System.out.println("ne postoji taj seller");
        }

        PaymentMethod paymentMethod = paymentMethodService.findOneById(dto.getPaymentMethodId());
        SellerPayment sellerPayment = new SellerPayment(paymentMethod, false);

        boolean postoji = false;

        for(SellerPayment sp: seller.getPaymentMethods()){
            if(sp.getPaymentMethod().getId() == paymentMethod.getId()){
                System.out.println("za ovog sellera vec postoji ovaj nacin placanja");
                postoji = true;
                break;
            }
        }

        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();

        if(!postoji){
            System.out.println("za ovog sellera ne postoji ovaj nacin placanja");

            seller.getPaymentMethods().add(sellerPayment);
            seller = sellerService.save(seller);


            if(paymentMethod.getName().equals("bitcoin")){
                System.out.println("bitcoin");

                HttpEntity<CustomerRequestDTO> httpRequest = new HttpEntity<>(new CustomerRequestDTO(dto.getSellerId(), dto.getName()));
                ResponseEntity<CustomerResponseDTO> response = restTemplate.postForEntity("https://localhost:8090/bitcoin-service/newSeller", httpRequest, CustomerResponseDTO.class);

                customerResponseDTO.setCustomerId(response.getBody().getCustomerId());
                customerResponseDTO.setRedirectionUrl(response.getBody().getRedirectionUrl());

            }else if(paymentMethod.getName().equals("bank")){
                System.out.println("bank");

                HttpEntity<CustomerRequestDTO> httpRequest = new HttpEntity<CustomerRequestDTO>(new CustomerRequestDTO(dto.getSellerId(), dto.getName()));
                ResponseEntity<CustomerResponseDTO> response = restTemplate.postForEntity("https://localhost:8086/banka-service/bankservice/newCustomer", httpRequest, CustomerResponseDTO.class);

                customerResponseDTO.setCustomerId(response.getBody().getCustomerId());
                customerResponseDTO.setRedirectionUrl(response.getBody().getRedirectionUrl());

            }else if(paymentMethod.getName().equals("paypal")){
                System.out.println("paypal");
                System.out.println("SELLERID:");
                System.out.println(dto.getSellerId());
                HttpEntity<CustomerRequestDTO> httpRequest = new HttpEntity<CustomerRequestDTO>(new CustomerRequestDTO(dto.getSellerId(), dto.getName()));
                ResponseEntity<CustomerResponseDTO> response = restTemplate.postForEntity("https://localhost:8087/paypal/newSeller", httpRequest, CustomerResponseDTO.class);

                customerResponseDTO.setCustomerId(response.getBody().getCustomerId());
                customerResponseDTO.setRedirectionUrl(response.getBody().getRedirectionUrl());
            }

            return new ResponseEntity<CustomerResponseDTO>(customerResponseDTO, HttpStatus.OK);

        }

        customerResponseDTO.setRedirectionUrl("");
        customerResponseDTO.setCustomerId(null);
        return new ResponseEntity<CustomerResponseDTO>(customerResponseDTO, HttpStatus.OK);

    }

    @RequestMapping(value = "/updateSeller", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<?> updateSeller(@RequestBody UpdateSellerDTO updateSellerDTO){

        Seller seller = sellerService.findOneBySellerId(updateSellerDTO.getSellerId());

        for(SellerPayment sp: seller.getPaymentMethods()){
            if(sp.getPaymentMethod().getName().equals(updateSellerDTO.getPaymentMethodName())){
                sp.setPotvrdjeno(true);
            }
        }

        seller = sellerService.save(seller);

        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }


}
