package com.example.bankaservice.controller;

import com.example.bankaservice.dto.*;
import com.example.bankaservice.model.Customer;
import com.example.bankaservice.model.Transaction;
import com.example.bankaservice.model.TransactionState;
import com.example.bankaservice.service.CustomerService;
import com.example.bankaservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value="/bankservice")
public class PaymentController {

//    private final String SUCCESS_URL = "/bank-page";
//    private final String FAILED_URL = "FAILED";
//    private final String ERROR_URL = "";

    private final String NEW_CUSTOMER_URL = "bank-new-customer";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/payment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    private ResponseEntity<?> payment(@RequestBody PaymentDTO paymentDTO) {

        System.out.println("usao u payment");

        Customer customer = customerService.findOneBySellerId(paymentDTO.getSellerId());

        Transaction transaction = new Transaction();
        transaction.setAmount(paymentDTO.getAmount());
        transaction.setTimestamp(new Date());
        transaction.setState(TransactionState.IN_PROCESS);
        transaction.setCustomer(customer);
        transaction = transactionService.firstSave(transaction);

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(customer.getMerchantId(),
                customer.getMerchantPassword(), paymentDTO.getAmount(), transaction.getId(), transaction.getTimestamp(), "/bank-page", "/fail", "/error");

        HttpEntity<PaymentRequestDTO> HReq = new HttpEntity<PaymentRequestDTO>(paymentRequestDTO);
        System.out.println("merchantId: " + customer.getMerchantId() + "; merchantPassword: " + customer.getMerchantPassword()
                         + "; amount: " + paymentDTO.getAmount() + "; merchantOrderId: " + transaction.getId() + "; merchantTimestamp: " + transaction.getTimestamp());

        ResponseEntity<PaymentResponseDTO> response =  restTemplate.postForEntity("https://localhost:8082/bank/checkPayment", HReq, PaymentResponseDTO.class);
        System.out.println("payment url: " + response.getBody().getPaymentUrl());

        return response;
    }

//    @RequestMapping(value = "/paymentComplete", method = RequestMethod.POST)
//    private void paymentComplete(@RequestBody CardResponseDTO cardResponseDTO) {
//
//        System.out.println("transactionState: " + cardResponseDTO.getState()
//                          + "; paymentId: " + cardResponseDTO.getPaymentId());
//
//        Transaction transaction = transactionService.findOneById(cardResponseDTO.getPaymentId());
//        transaction.setState(cardResponseDTO.getState());
//        transaction = transactionService.save(transaction);
//    }

    @RequestMapping(value  = "/updateTransaction/{id}", method = RequestMethod.PUT)
    private ResponseEntity<String> updateTransaction(@RequestBody TransactionStateDTO transactionStateDTO, @PathVariable("id") String id){
        Transaction transaction = transactionService.findOneById(Integer.parseInt(id));
        transaction.setState(transactionStateDTO.getTransactionState());
        transaction = transactionService.save(transaction);
        return new ResponseEntity<>("[bank-service]: Transakcija update-ovana", HttpStatus.OK);
    }

    @RequestMapping(value  = "/newCustomer", method = RequestMethod.POST)
    private ResponseEntity<CustomerResponseDTO> newCustomer(@RequestBody CustomerRequestDTO customerRequestDTO){

        Customer customer = new Customer();
        customer.setSellerId(customerRequestDTO.getSellerId());
        customer.setName(customerRequestDTO.getName());
        customer = customerService.save(customer);

        return new ResponseEntity<CustomerResponseDTO>(new CustomerResponseDTO(NEW_CUSTOMER_URL, customer.getId()), HttpStatus.OK);
    }

    @RequestMapping(value  = "/postCustomerData", method = RequestMethod.POST)
    private ResponseEntity<?> updateCustomer(@RequestBody CustomerDTO customerDTO){

        System.out.println("id:" + customerDTO.getCustomerId());
        System.out.println("merchantId: " + customerDTO.getMerchantId());
        System.out.println("merchaantPassword: " + customerDTO.getMerchantPassword());

        Customer customer = customerService.findOneById(customerDTO.getCustomerId());
        customer.setMerchantId(customerDTO.getMerchantId());
        customer.setMerchantPassword(customerDTO.getMerchantPassword());
        customer = customerService.save(customer);

//        TODO poslati zahtev na sellers da stavi da je sellerPayment potvrdjeno true

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
