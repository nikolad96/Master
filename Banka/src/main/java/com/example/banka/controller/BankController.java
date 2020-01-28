package com.example.banka.controller;

import com.example.banka.dto.*;
import com.example.banka.model.Account;
import com.example.banka.model.Customer;
import com.example.banka.model.Transaction;
import com.example.banka.model.TransactionState;
import com.example.banka.service.AccountService;
import com.example.banka.service.CustomerService;
import com.example.banka.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@CrossOrigin
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

    @RequestMapping(value = "/checkPayment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> checkPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {

        Customer merchant = customerService.findOneByMerchantId(paymentRequestDTO.getMerchantId());
        System.out.println("merchantId: " + merchant.getMerchantId() + "; merchantPassword: " + merchant.getMerchantPassword());
        System.out.println("paymentRequest - merchantPassword: " + paymentRequestDTO.getMerchantPassword());
        System.out.println("merchantOrderId: " + paymentRequestDTO.getMerchantOrderId());

        Transaction transaction = new Transaction();
        transaction.setMerchantOrderId(paymentRequestDTO.getMerchantOrderId());
        transaction.setAmount(paymentRequestDTO.getAmount());
//        transaction.setTimestamp(paymentRequestDTO.getMerchantTimestamp());
        transaction.setTimestamp(new Date());
        transaction.setCustomer(merchant);

        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();

        if(!isValidRequest(paymentRequestDTO, merchant)){
            System.out.println("Zahtev nije ispravan.");

            transaction.setState(TransactionState.FAILED);
            transaction = transactionService.save(transaction);
            updateTransactionBankService(transaction);

            paymentResponseDTO.setPaymentUrl(paymentRequestDTO.getFailedUrl());
            paymentResponseDTO.setPaymentId(null);

            return new ResponseEntity<PaymentResponseDTO>(paymentResponseDTO, HttpStatus.OK);
        }

        System.out.println("Zahtev je ispravan.");
        transaction.setState(TransactionState.IN_PROCESS);
        transaction = transactionService.save(transaction);

        paymentResponseDTO.setPaymentUrl(paymentRequestDTO.getSuccessUrl());
        paymentResponseDTO.setPaymentId(transaction.getMerchantOrderId());

        return new ResponseEntity<PaymentResponseDTO>(paymentResponseDTO, HttpStatus.OK);

    }


    @RequestMapping(value = "/checkAccountAcquirer", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> checkAccountAcquirer(@RequestBody CardRequestDTO cardRequestDTO) {

        Transaction transaction = transactionService.findOneByMerchantOrderId(cardRequestDTO.getPaymentId());
        Customer acquirer = transaction.getCustomer();

        Account account = accountService.findOneByPan(cardRequestDTO.getPan());
        Customer issuer = new Customer();
        try{
            issuer = account.getCustomer();
        }catch(NullPointerException e){
            System.out.println("Nije dobar pan");

            transaction.setState(TransactionState.ERROR);
            transaction = transactionService.save(transaction);
            updateTransactionBankService(transaction);

            return new ResponseEntity<AcquirerResponseDTO>(new AcquirerResponseDTO(transaction.getId(), transaction.getTimestamp(), transaction.getState(),
                    "Greska u podacima kartice."), HttpStatus.BAD_REQUEST);
        }

        transaction.setState(TransactionState.IN_PROCESS);
        transaction = transactionService.save(transaction);

        if(issuer.getAccount().getBank().getId() != acquirer.getAccount().getBank().getId()){
            System.out.println("Banka prodavca nije ista kao banka kupca");
            PccRequestDTO pccRequestDTO = new PccRequestDTO();
            pccRequestDTO.setAcquirerOrderId(transaction.getId());
            pccRequestDTO.setAcquirerTimestamp(transaction.getTimestamp());
            pccRequestDTO.setAmount(transaction.getAmount());
            pccRequestDTO.setState(transaction.getState());
            pccRequestDTO.setCardRequestDTO(cardRequestDTO);

            //prosledjivanje zahteva ka pcc-u jer nisu iste banke
            HttpEntity<PccRequestDTO> entity = new HttpEntity<PccRequestDTO>(pccRequestDTO);
            ResponseEntity<IssuerResponseDTO> responseEntity = restTemplate.postForEntity("http://localhost:8092/pcc/forward", entity, IssuerResponseDTO.class);
            return responseEntity;

        }

        List<Account> accounts = accountService.findAccount(cardRequestDTO.getPan(), cardRequestDTO.getSecurityCode(), cardRequestDTO.getCardholderName(), cardRequestDTO.getExpirationDate());
        System.out.println("accounts size: " + accounts.size());

        if(accounts.size() == 0 || accounts.size() > 1){
            System.out.println("Greska u podacima kartice.");


            transaction.setState(TransactionState.ERROR);
            transaction = transactionService.save(transaction);
            updateTransactionBankService(transaction);

            return new ResponseEntity<AcquirerResponseDTO>(new AcquirerResponseDTO(transaction.getId(), transaction.getTimestamp(), transaction.getState(),
                    "Greska u podacima kartice."), HttpStatus.BAD_REQUEST);
        }else{
            System.out.println("Podaci sa kartice su dobri");

            account = accounts.get(0);
            if(accountService.isCardExpired(accounts.get(0))){
                System.out.println("Kartica je istekla.");

                transaction.setState(TransactionState.ERROR);
                transaction = transactionService.save(transaction);
                updateTransactionBankService(transaction);

                return new ResponseEntity<AcquirerResponseDTO>(new AcquirerResponseDTO(transaction.getId(), transaction.getTimestamp(), transaction.getState(),
                        "Kartica je istekla."), HttpStatus.BAD_REQUEST);
            }else{
                System.out.println("Nije istekla kartica.");

                if(account.getBalance() >= transaction.getAmount()){
                    System.out.println("Ima dovoljno sredstava na kartici.");

//                    account.setBalance(account.getBalance() - transaction.getAmount());
                    account.setReserved(account.getReserved() + transaction.getAmount());
                    account = accountService.save(account);

//                 transaction.setState(TransactionState.SUCCESS);
//                 transaction = transactionService.save(transaction);

                }else{
                    System.out.println("Nema dovoljno sredstava na kartici.");

                    transaction.setState(TransactionState.NOT_ENOUGH_MONEY);
                    transaction = transactionService.save(transaction);
                    updateTransactionBankService(transaction);

                    return new ResponseEntity<AcquirerResponseDTO>(new AcquirerResponseDTO(transaction.getId(), transaction.getTimestamp(), transaction.getState(),
                            "Nema dovoljno sredstava na kartici."), HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<AcquirerResponseDTO>(new AcquirerResponseDTO(transaction.getId(), transaction.getTimestamp(), transaction.getState(),
                    "Uspesna transakcija."), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/checkAccountIssuer", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> checkAccountIssuer(@RequestBody PccRequestDTO pccRequestDTO) {

        CardRequestDTO cardRequestDTO = pccRequestDTO.getCardRequestDTO();
        Transaction transaction = transactionService.findOneByMerchantOrderId(cardRequestDTO.getPaymentId());

        List<Account> accounts = accountService.findAccount(cardRequestDTO.getPan(), cardRequestDTO.getSecurityCode(), cardRequestDTO.getCardholderName(), cardRequestDTO.getExpirationDate());
        System.out.println("accounts size: " + accounts.size());

        if(accounts.size() == 0 || accounts.size() > 1){
            System.out.println("Greska u podacima kartice.");

            transaction.setState(TransactionState.ERROR);
            transaction = transactionService.save(transaction);
            updateTransactionBankService(transaction);
            updateTransactionPcc(transaction);

            return new ResponseEntity<IssuerResponseDTO>(new IssuerResponseDTO(transaction.getState(), transaction.getId(),
                    transaction.getTimestamp(), transaction.getId(), transaction.getTimestamp(), "Greska u podacima kartice."), HttpStatus.OK);
        }else{
            System.out.println("Podaci sa kartice su dobri");

            Account account = accounts.get(0);
            if(accountService.isCardExpired(accounts.get(0))){
                System.out.println("Kartica je istekla.");

                transaction.setState(TransactionState.ERROR);
                transaction = transactionService.save(transaction);
                updateTransactionBankService(transaction);
                updateTransactionPcc(transaction);

                return new ResponseEntity<IssuerResponseDTO>(new IssuerResponseDTO(transaction.getState(), transaction.getId(),
                        transaction.getTimestamp(), transaction.getId(), transaction.getTimestamp(), "Kartica je istekla."), HttpStatus.OK);
            }else{
                System.out.println("Nije istekla kartica.");

                if(account.getBalance() >= transaction.getAmount()){
                    System.out.println("Ima dovoljno sredstava na kartici.");

                    account.setReserved(account.getReserved() + transaction.getAmount());
                    account = accountService.save(account);

                }else{
                    System.out.println("Nema dovoljno sredstava na kartici.");

                    transaction.setState(TransactionState.NOT_ENOUGH_MONEY);
                    transaction = transactionService.save(transaction);
                    updateTransactionBankService(transaction);
                    updateTransactionPcc(transaction);

                    return new ResponseEntity<IssuerResponseDTO>(new IssuerResponseDTO(transaction.getState(), transaction.getId(),
                            transaction.getTimestamp(), transaction.getId(), transaction.getTimestamp(), "Nema dovoljno sredstava na kartici."), HttpStatus.OK);
                }
            }
            return new ResponseEntity<IssuerResponseDTO>(new IssuerResponseDTO(transaction.getState(), transaction.getId(),
                    transaction.getTimestamp(), transaction.getId(), transaction.getTimestamp(), "Uspesna transakcija."), HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/executePayment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> executePayment(@RequestBody CardRequestDTO cardRequestDTO) {

        Transaction transaction = transactionService.findOneByMerchantOrderId(cardRequestDTO.getPaymentId());
        Account account = accountService.findOneByPan(cardRequestDTO.getPan());

        account.setBalance(account.getBalance() - transaction.getAmount());
        account.setReserved(account.getReserved() - transaction.getAmount());
        account = accountService.save(account);

        transaction.setState(TransactionState.SUCCESS);
        transaction = transactionService.save(transaction);

        updateTransactionBankService(transaction);
        updateTransactionPcc(transaction);
        updateKupovinaNC(transaction.getMerchantOrderId());

        FinalResponseDTO finalResponseDTO = new FinalResponseDTO(transaction.getMerchantOrderId(), transaction.getId(),
                transaction.getTimestamp(), transaction.getMerchantOrderId(), transaction.getState());

        return new ResponseEntity<FinalResponseDTO>(finalResponseDTO, HttpStatus.OK);

    }

    private boolean isValidRequest(PaymentRequestDTO paymentRequestDTO, Customer merchant){

        if(merchant == null){
            return false;
        }

        if(!merchant.getMerchantPassword().equals(paymentRequestDTO.getMerchantPassword())){
            return false;
        }

        if(paymentRequestDTO.getAmount() == 0){
            return false;
        }

        if(paymentRequestDTO.getMerchantTimestamp() == null){
            return false;
        }

        if(paymentRequestDTO.getMerchantOrderId() == null){
            return false;
        }

        return true;

    }

    private void updateTransactionBankService(Transaction transaction) {
        HttpEntity<TransactionStateDTO> entity = new HttpEntity<TransactionStateDTO>(new TransactionStateDTO(transaction.getState()));
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8085/bankservice/updateTransaction/" + transaction.getMerchantOrderId(),
                HttpMethod.PUT, entity, String.class);
    }

    private void updateTransactionPcc(Transaction transaction) {
        HttpEntity<TransactionStateDTO> entity = new HttpEntity<TransactionStateDTO>(new TransactionStateDTO(transaction.getState()));
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8092/pcc/updateTransaction/" + transaction.getId(),
                HttpMethod.PUT, entity, String.class);
    }

    private void updateKupovinaNC(Integer paymentId) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8096/KP/updateKupovina/" + paymentId, String.class);
    }

}
