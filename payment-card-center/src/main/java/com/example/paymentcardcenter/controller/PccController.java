package com.example.paymentcardcenter.controller;

import com.example.paymentcardcenter.dto.IssuerResponseDTO;
import com.example.paymentcardcenter.dto.PccRequestDTO;
import com.example.paymentcardcenter.dto.TransactionStateDTO;
import com.example.paymentcardcenter.model.Transaction;
import com.example.paymentcardcenter.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value="/pcc")
public class PccController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/forward", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    private ResponseEntity<?> forward(@RequestBody PccRequestDTO pccRequestDTO) {

        if(!isValidRequest(pccRequestDTO)){
            return new ResponseEntity<>("Greska u podacima.", HttpStatus.BAD_REQUEST);
        }

        Transaction transaction = new Transaction();
        transaction.setId(pccRequestDTO.getAcquirerOrderId());
        transaction.setAmount(pccRequestDTO.getAmount());
        transaction.setState(pccRequestDTO.getState());
        transaction.setTimestamp(pccRequestDTO.getAcquirerTimestamp());
//        transaction.getCustomerId(pccRequestDTO);
        transaction = transactionService.save(transaction);

        HttpEntity<PccRequestDTO> entity = new HttpEntity<>(pccRequestDTO);
        ResponseEntity<IssuerResponseDTO> responseEntity = restTemplate.postForEntity("http://localhost:8093/bank/checkAccountIssuer", entity, IssuerResponseDTO.class);

        return responseEntity;
    }

    @RequestMapping(value  = "/updateTransaction/{id}", method = RequestMethod.PUT)
    private ResponseEntity<String> updateTransaction(@RequestBody TransactionStateDTO paymentStatusDTO, @PathVariable("id") String id){
        Transaction transaction = transactionService.findOneById(Integer.parseInt(id));
        transaction.setState(paymentStatusDTO.getTransactionState());
        transaction = transactionService.save(transaction);
        return new ResponseEntity<>("[PCC] Transakcija update-ovana", HttpStatus.OK);
    }

    private boolean isValidRequest(PccRequestDTO pccRequestDTO){

        if(pccRequestDTO.getAcquirerOrderId() == null || pccRequestDTO.getAcquirerOrderId() == 0){
            return false;
        }

        if(pccRequestDTO.getAcquirerTimestamp() == null){
            return false;
        }

        if(pccRequestDTO.getAmount() == 0){
            return false;
        }

        if(pccRequestDTO.getCardRequestDTO().getPan().equals("")){
            return false;
        }

        if(pccRequestDTO.getCardRequestDTO().getCardholderName().equals("")){
            return false;
        }

        if(pccRequestDTO.getCardRequestDTO().getExpirationDate().equals("")){
            return false;
        }

        if(pccRequestDTO.getCardRequestDTO().getSecurityCode().equals("")){
            return false;
        }

        return true;
    }

}
