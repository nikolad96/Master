package com.example.bitcoinservice.controller;

import com.example.bitcoinservice.dto.GetResponseDTO;
import com.example.bitcoinservice.dto.StatusDTO;
import com.example.bitcoinservice.model.Transaction;
import com.example.bitcoinservice.model.TransactionStatus;
import com.example.bitcoinservice.repo.TransactionRepo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Service
public class Monitor {

    @Autowired
    HttpComponentsClientHttpRequestFactory requestFactory;



    @Autowired
    TransactionRepo transactionRepo;

    private Integer id;
    private String secret;
    private Integer rad_id;
    private Integer casopis_id;
    private Integer korisnik_id;


//    public Monitor(Integer id, String secret, Integer rad_id, Integer casopis_id, Integer korisnik_id){
//        this.id = id;
//        this.secret = secret;
//        this.rad_id = rad_id;
//        this.casopis_id = casopis_id;
//        this.korisnik_id = korisnik_id;
//
//    }

    public Monitor(){};

    public void mon(Integer id, String secret, Integer rad_id, Integer casopis_id, Integer korisnik_id){
        RestTemplate REST_template = new RestTemplate(this.requestFactory);
        while(true) {



            JSONObject get_body = new JSONObject();
//            int id = this.id;
            get_body.put("id", id);
            HttpHeaders post_header = new HttpHeaders();
            post_header.set("Authorization", "Token " + secret);
            HttpEntity<?> get_request = new HttpEntity<>(post_header);
            System.out.println(id);
            ResponseEntity<GetResponseDTO> get_response;
            String status = null;
            try {
                System.out.println(get_request);
                get_response = REST_template.exchange("https://api-sandbox.coingate.com/v2/orders/" + id, HttpMethod.GET, get_request, GetResponseDTO.class);
                System.out.println(get_response);
                status = new String((String) get_response.getBody().getStatus());
            } catch (Exception e) {
                e.printStackTrace();
            }


            System.out.println(status);

            if (status.equals("paid")){
                // PRINT INFO SUCCESS
                Transaction transaction = transactionRepo.findOneById(id);
                transaction.setTransactionStatus(TransactionStatus.PAID);
                transactionRepo.save(transaction);
                StatusDTO statusDTO = new StatusDTO();
                statusDTO.setStatus(status);
                statusDTO.setMessage("Uspesno placeno");
                REST_template.postForEntity("http://localhost:8096/KP/paid" + rad_id + "/" + casopis_id + "/" + korisnik_id, statusDTO, String.class);
                return;
            }

            else if(status.equals("invalid")){
                // PRINT INFO INVALID EXPIRED OR CANCELED
                Transaction transaction = transactionRepo.findOneById(id);
                transaction.setTransactionStatus(TransactionStatus.INVALID);
                transactionRepo.save(transaction);
                StatusDTO statusDTO = new StatusDTO();
                statusDTO.setStatus(status);
                statusDTO.setMessage("Transakcija je invalidna");
                REST_template.postForEntity("http://localhost:8096/KP/paid" + rad_id + "/" + casopis_id + "/" + korisnik_id, statusDTO, String.class);

                return;
            }

            else if(status.equals("expired")) {
                Transaction transaction = transactionRepo.findOneById(id);
                transaction.setTransactionStatus(TransactionStatus.EXPIRED);
                transactionRepo.save(transaction);
                StatusDTO statusDTO = new StatusDTO();
                statusDTO.setStatus(status);
                statusDTO.setMessage("Isteklo je vreme za transakciju");
                REST_template.postForEntity("http://localhost:8096/KP/paid" + rad_id + "/" + casopis_id + "/" + korisnik_id, statusDTO, String.class);

                return;
            }

            else if(status.equals("canceled")){
                Transaction transaction = transactionRepo.findOneById(id);
                transaction.setTransactionStatus(TransactionStatus.CANCELLED);
                transactionRepo.save(transaction);
                StatusDTO statusDTO = new StatusDTO();
                statusDTO.setStatus(status);
                statusDTO.setMessage("Otkazali ste transkaciju");
                return;
            }

            else {
                System.out.println(status);

                try {
                    Thread.sleep(5000);
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }


    }

//    public void run (){
//        RestTemplate REST_template = new RestTemplate(this.requestFactory);
//        while(true) {
//
//
//
//            JSONObject get_body = new JSONObject();
//            int id = this.id;
//            get_body.put("id", id);
//            HttpHeaders post_header = new HttpHeaders();
//            post_header.set("Authorization", "Token " + this.secret);
//            HttpEntity<?> get_request = new HttpEntity<>(post_header);
//            System.out.println(id);
//            ResponseEntity<GetResponseDTO> get_response;
//            String status = null;
//            try {
//                System.out.println(get_request);
//                get_response = REST_template.exchange("https://api-sandbox.coingate.com/v2/orders/" + id, HttpMethod.GET, get_request, GetResponseDTO.class);
//                System.out.println(get_response);
//                status = new String((String) get_response.getBody().getStatus());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            System.out.println(status);
//
//            if (status.equals("paid")){
//                // PRINT INFO SUCCESS
//                Transaction transaction = transactionRepo.findOneById(id);
//                transaction.setTransactionStatus(TransactionStatus.PAID);
//                transactionRepo.save(transaction);
//                StatusDTO statusDTO = new StatusDTO();
//                statusDTO.setStatus(status);
//                statusDTO.setMessage("Uspesno placeno");
//                REST_template.postForEntity("http://localhost:8096/KP/paid" + rad_id + "/" + casopis_id + "/" + korisnik_id, statusDTO, String.class);
//                return;
//            }
//
//            else if(status.equals("invalid")){
//                // PRINT INFO INVALID EXPIRED OR CANCELED
//                Transaction transaction = transactionRepo.findOneById(id);
//                transaction.setTransactionStatus(TransactionStatus.INVALID);
//                transactionRepo.save(transaction);
//                StatusDTO statusDTO = new StatusDTO();
//                statusDTO.setStatus(status);
//                statusDTO.setMessage("Transakcija je invalidna");
//                REST_template.postForEntity("http://localhost:8096/KP/paid" + rad_id + "/" + casopis_id + "/" + korisnik_id, statusDTO, String.class);
//
//                return;
//            }
//
//            else if(status.equals("expired")) {
//                Transaction transaction = transactionRepo.findOneById(id);
//                transaction.setTransactionStatus(TransactionStatus.EXPIRED);
//                transactionRepo.save(transaction);
//                StatusDTO statusDTO = new StatusDTO();
//                statusDTO.setStatus(status);
//                statusDTO.setMessage("Isteklo je vreme za transakciju");
//                REST_template.postForEntity("http://localhost:8096/KP/paid" + rad_id + "/" + casopis_id + "/" + korisnik_id, statusDTO, String.class);
//
//                return;
//            }
//
//            else if(status.equals("canceled")){
//                Transaction transaction = transactionRepo.findOneById(id);
//                transaction.setTransactionStatus(TransactionStatus.CANCELLED);
//                transactionRepo.save(transaction);
//                    StatusDTO statusDTO = new StatusDTO();
//                    statusDTO.setStatus(status);
//                    statusDTO.setMessage("Otkazali ste transkaciju");
//                return;
//            }
//
//            else {
//                System.out.println(status);
//
//                try {
//                    Thread.sleep(5000);
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//
//    }
}
