package com.example.bankaservice.controller;

import com.example.bankaservice.dto.*;
import com.example.bankaservice.model.Customer;
import com.example.bankaservice.model.Transaction;
import com.example.bankaservice.service.CustomerService;
import com.example.bankaservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequestMapping(value="/bankservice")
public class PaymentController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    private ResponseEntity<?> payment(@RequestBody PaymentDTO paymentDTO) {

        Customer customer = customerService.findOneByMerchantId(paymentDTO.getMerchantId());

        Transaction transaction = new Transaction();
        transaction.setAmount(paymentDTO.getAmount());
        transaction.setTimestamp(new Date());
        transaction.setState(TransactionState.IN_PROCESS);
        transaction.setCustomer(customer);
        transaction = transactionService.save(transaction);

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(customer.getMerchantId(),
                customer.getMerchantPassword(), paymentDTO.getAmount(), transaction.getId(), transaction.getTimestamp());

        HttpEntity<PaymentRequestDTO> HReq=new HttpEntity<PaymentRequestDTO>(paymentRequestDTO);
        System.out.println("merchantId: " + customer.getMerchantId() + "; merchantPassword: " + customer.getMerchantPassword()
                         + "; amount: " + paymentDTO.getAmount() + "; merchanetOrderId: " + transaction.getId() + "; merchantTimestamp: " + transaction.getTimestamp());

        ResponseEntity<PaymentResponseDTO> response =  restTemplate.postForEntity("https://localhost:8082/bank/checkPayment", HReq, PaymentResponseDTO.class);
        System.out.println("payment url: " + response.getBody().getPaymentUrl());

        if(response.getBody().getPaymentUrl() == "neuspesno") {
            transaction.setState(TransactionState.ERROR);
            transaction = transactionService.save(transaction);
        }

        //staviti u neku klasu merchantOrderId, paymentId, paymentUrl

        return response;
    }

    @RequestMapping(value = "/paymentComplete", method = RequestMethod.POST)
    private void paymentComplete(@RequestBody CardResponseDTO cardResponseDTO) {

        System.out.println("transactionState: " + cardResponseDTO.getState() + "; merchantOrderId: " + cardResponseDTO.getMerchantOrderId()
                          + "; paymentId: " + cardResponseDTO.getPaymentId());

        Transaction transaction = transactionService.findOneById(cardResponseDTO.getMerchantOrderId());
        transaction.setState(cardResponseDTO.getState());
        transaction = transactionService.save(transaction);
    }

}
