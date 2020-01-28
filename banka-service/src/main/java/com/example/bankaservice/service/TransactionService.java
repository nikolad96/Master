package com.example.bankaservice.service;

import com.example.bankaservice.dto.TransactionStateDTO;
import com.example.bankaservice.model.Transaction;
import com.example.bankaservice.model.TransactionState;
import com.example.bankaservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Transaction findOneById(Integer id){ return transactionRepository.findOneById(id); }
    public List<Transaction> findAll(){
        return transactionRepository.findAll();
    }
    public Transaction save(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public Transaction firstSave(Transaction transaction){

        transaction = transactionRepository.save(transaction);

        final Transaction finalTransaction = transaction;
        CompletableFuture<Object> completableFuture = CompletableFuture.supplyAsync(() -> {

            Timer timer = new Timer();
            long pocetak = System.currentTimeMillis();

            //na svakih 10 sekundi proverava da li se promenio status transakcije
            //nakon 5min, transakcija automatski postaje neuspesna
            timer.schedule(new TimerTask(){

                @Override
                public void run(){

                    System.out.println("--pocetak provere--");
                    long trenutno = System.currentTimeMillis();
                    Transaction transactionHelp = transactionRepository.findOneById(finalTransaction.getId());

                    if(transactionHelp.getState().equals(TransactionState.SUCCESS)){
                        System.out.println("--transakcija je uspesna--");
                        timer.cancel();
                    }

                    if(transactionHelp.getState().equals(TransactionState.ERROR) || transactionHelp.getState().equals(TransactionState.FAILED) || transactionHelp.getState().equals(TransactionState.NOT_ENOUGH_MONEY)){
                        System.out.println("--transakcija je neuspesna--");
                        timer.cancel();
                    }

                    if(trenutno - pocetak > 300000){
                        System.out.println("--proslo 5min -> neuspesna transakcija--");
                        transactionHelp.setState(TransactionState.ERROR);
                        transactionHelp = transactionRepository.save(transactionHelp);
                        updateTransactionBank(transactionHelp);
                        timer.cancel();
                    }
                }
            },0,10000);

            return "end";
        });

        return transaction;
    }

    private void updateTransactionBank(Transaction transaction) {
        HttpEntity<TransactionStateDTO> entity = new HttpEntity<TransactionStateDTO>(new TransactionStateDTO(transaction.getState()));
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8082/bank/updateTransaction/" + transaction.getId(),
                HttpMethod.PUT, entity, String.class);
    }
}
