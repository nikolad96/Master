package com.example.sellers.controller;


import com.example.sellers.dto.PaymentMethodDTO;
import com.example.sellers.dto.PaymentMethodListDTO;
import com.example.sellers.dto.SellerBtcDTO;
import com.example.sellers.dto.SellerDTO;
import com.example.sellers.model.PaymentMethod;
import com.example.sellers.model.Seller;
import com.example.sellers.repository.SellerRepository;
import com.example.sellers.service.SellerService;
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
    private SellerService sellerService;

    @RequestMapping(method = RequestMethod.GET, path="/getSellers", produces = "application/json")
    public @ResponseBody List<SellerDTO> getSellers(){
        List<Seller> sellers = sellerService.findAll();
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
        Seller seller = sellerService.findOneByName(sellerName);
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

        seller.setSellerId(dto.getId());
        seller.setName(dto.getName());
        seller.setPib(dto.getPib());
        seller.setPaymentMethods(dto.getPaymentMethods());

        for(PaymentMethod pm : dto.getPaymentMethods()){
            if(pm.getName().equals("bitcoin")){
                SellerBtcDTO btcDto = new SellerBtcDTO();
                btcDto.setId(dto.getId());
                btcDto.setSecret(dto.getSecret());

                ResponseEntity<SellerDTO> res = REST_template.postForEntity("https://localhost:8090/bitcoin-service/newSeller", btcDto, SellerDTO.class);

            }
        }

        try {
            sellerService.save(seller);

        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.OK);

    }

    @RequestMapping(value = "/getPaymentMethods/{casopisId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<PaymentMethodListDTO> getPaymentMethods(@PathVariable("casopisId") Integer casopisId){
        Seller seller = sellerService.findOneBySellerId(casopisId);
        List<PaymentMethodDTO> lista = new ArrayList<>();

        for(PaymentMethod method : seller.getPaymentMethods()){
            PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO(method.getId(), method.getName());
            lista.add(paymentMethodDTO);
        }

        PaymentMethodListDTO paymentMethodListDTO = new PaymentMethodListDTO();
        paymentMethodListDTO.setMethods(lista);

        return new ResponseEntity<PaymentMethodListDTO>(paymentMethodListDTO, HttpStatus.OK);
    }



}
