package com.example.sellers.controller;


import com.example.sellers.dto.PaymentMethodsDTO;
import com.example.sellers.dto.SellerBtcDTO;
import com.example.sellers.dto.SellerDTO;
import com.example.sellers.model.PaymentMethod;
import com.example.sellers.model.PaymentMethods;
import com.example.sellers.model.Seller;
import com.example.sellers.repo.SellersRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    RestTemplate REST_template;

    @Autowired
    SellersRepo sellersRepo;

    @RequestMapping(method = RequestMethod.GET, path="/getSellers", produces = "application/json")
    public @ResponseBody List<SellerDTO> getSellers(){
        List<Seller> sellers = sellersRepo.findAll();
        List<SellerDTO> payload = new ArrayList<>();

        for(Seller seller : sellers){
            SellerDTO dto = new SellerDTO();
            dto.setId(seller.getId());
            dto.setName(seller.getName());
            dto.setPib(seller.getPib());
            dto.setPaymentMethods(seller.getPaymentMethods());
            payload.add(dto);
        }

        return payload;
    }

    @RequestMapping(method = RequestMethod.GET, path="/getSeller/{sellerName}", produces = "application/json")
    public @ResponseBody SellerDTO getSeller(@PathVariable String sellerName){
        Seller seller = sellersRepo.findOneByName(sellerName);
        SellerDTO payload = new SellerDTO();
        payload.setId(seller.getId());
        payload.setName(seller.getName());
        payload.setPib(seller.getPib());
        payload.setPaymentMethods(seller.getPaymentMethods());

        return payload;
    }

    @RequestMapping(method = RequestMethod.POST, path="/putSeller", produces = "application/json")
    public @ResponseBody ResponseEntity putSeller(@RequestBody SellerDTO dto) throws Exception{
        Seller seller = new Seller();

        seller.setId(dto.getId());
        seller.setName(dto.getName());
        seller.setPib(dto.getPib());
        seller.setPaymentMethods(dto.getPaymentMethods());

        for(PaymentMethod pm : dto.getPaymentMethods()){
            if(pm.getName().equals("bitcoin")){
                SellerBtcDTO btcDto = new SellerBtcDTO();
                btcDto.setId(dto.getId());
                btcDto.setSecret(dto.getSecret());

                ResponseEntity<SellerDTO> res = REST_template.postForEntity("http://localhost:8090/bitcoin-service/newSeller", btcDto, SellerDTO.class);

            }
        }

        try {
            sellersRepo.save(seller);

        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.OK);

    }

    @RequestMapping(value = "/getPaymentMethods", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody PaymentMethodsDTO getPaymentMethods(@RequestBody PaymentMethodsDTO dto){
        Seller s = sellersRepo.findOneById(dto.getId());
        List<PaymentMethods> lista = new ArrayList<>();

        for(PaymentMethod method : s.getPaymentMethods()){
            if(method.getName().equals("bank")){
                lista.add(PaymentMethods.BANK);
            }

            else if(method.getName().equals("paypal")){
                lista.add(PaymentMethods.PAYPAL);
            }

            else if(method.getName().equals("bitcoin")){
                lista.add(PaymentMethods.BITCOIN);
            }
        }

        dto.setPaymentMethods(lista);

        return dto;
    }



}
