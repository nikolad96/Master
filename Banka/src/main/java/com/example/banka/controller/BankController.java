package com.example.banka.controller;

import com.example.banka.dto.*;
import com.example.banka.model.Account;
import com.example.banka.model.Customer;
import com.example.banka.model.Transaction;
import com.example.banka.service.AccountService;
import com.example.banka.service.CustomerService;
import com.example.banka.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(value="/bank")
public class BankController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/checkPayment", method = RequestMethod.POST)
    public ResponseEntity<?> checkPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {


        boolean merchant_OK = false;

        Customer merchant = customerService.findOneByMerchantId(paymentRequestDTO.getMerchantId());
        System.out.println("merchantId: " + merchant.getMerchantId() + "; merchantPassword: " + merchant.getMerchantPassword());
        System.out.println("paymentRequest - merchantPassword: " + paymentRequestDTO.getMerchantPassword());

        Transaction transaction = new Transaction();
        transaction.setId(paymentRequestDTO.getMerchantOrderId());
        transaction.setAmount(paymentRequestDTO.getAmount());
        transaction.setTimestamp(paymentRequestDTO.getMerchantTimestamp());
        transaction.setCustomer(merchant);

        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();

        try {
            if (merchant.getMerchantPassword().equals(paymentRequestDTO.getMerchantPassword())) {
                merchant_OK = true;
                System.out.println("Zahtev je ispravan.");
                transaction.setState(TransactionState.IN_PROCESS);
                paymentResponseDTO.setPaymentUrl("uspesno");
            }else{
                System.out.println("Zahtev nije ispravan.");
                transaction.setState(TransactionState.ERROR);
                paymentResponseDTO.setPaymentUrl("neuspesno");
            }
        }catch (Exception e){
            System.out.println("Zahtev nije ispravan.");
            transaction.setState(TransactionState.ERROR);
            paymentResponseDTO.setPaymentUrl("neuspesno");
        }

        transaction = transactionService.save(transaction);
        paymentResponseDTO.setPaymentId(transaction.getId());

        return new ResponseEntity<PaymentResponseDTO>(paymentResponseDTO, HttpStatus.OK);

    }

    @RequestMapping(value = "/checkAccount", method = RequestMethod.POST)
    public ResponseEntity<?> checkAccount(@RequestBody CardRequestDTO cardRequestDTO) {

        CardResponseDTO cardResponseDTO = new CardResponseDTO();
        cardResponseDTO.setPaymentId(cardRequestDTO.getPaymentId());
        cardResponseDTO.setMerchantOrderId(cardRequestDTO.getMerchantOrderId());

        Transaction transaction = transactionService.findOneById(cardRequestDTO.getPaymentId());
        List<Account> accounts = accountService.findAccount(cardRequestDTO.getPan(), cardRequestDTO.getSecurityCode(), cardRequestDTO.getCardholderName(), cardRequestDTO.getExpirationDate());
        System.out.println("accounts size: " + accounts.size());

        if(accounts.size() == 0 || accounts.size() > 1){
            System.out.println("Greska u podacima kartice.");
            cardResponseDTO.setState(TransactionState.ERROR);

            transaction.setState(TransactionState.ERROR);
            transaction = transactionService.save(transaction);

            return new ResponseEntity<CardResponseDTO>(cardResponseDTO, HttpStatus.OK);
        }else{
            System.out.println("Podaci sa kartice su dobri");
            Account account = accounts.get(0);

             if(account.getBalance() >= transaction.getAmount()){
                 System.out.println("Ima dovoljno sredstava na kartici.");

                 account.setBalance(account.getBalance() - transaction.getAmount());
                 account.setReserved(account.getReserved() + transaction.getAmount());
                 account = accountService.save(account);

                 transaction.setState(TransactionState.SUCCESS);
                 transaction = transactionService.save(transaction);

                 cardResponseDTO.setState(TransactionState.SUCCESS);
             }else{
                 System.out.println("Nema dovoljno sredstava na kartici.");

                 transaction.setState(TransactionState.NOT_ENOUGH_MONEY);
                 transaction = transactionService.save(transaction);

                 cardResponseDTO.setState(TransactionState.NOT_ENOUGH_MONEY);

             }

            HttpEntity<CardResponseDTO> HReq=new HttpEntity<CardResponseDTO>(cardResponseDTO);
            restTemplate.postForEntity("http://localhost:8085/bankservice/paymentComplete", HReq, CardResponseDTO.class);

            return new ResponseEntity<CardResponseDTO>(cardResponseDTO, HttpStatus.OK);
        }


    }

}
