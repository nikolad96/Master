package com.example.bitcoinservice.controller;


import com.example.bitcoinservice.dto.*;
import com.example.bitcoinservice.model.Seller;
import com.example.bitcoinservice.model.Transaction;
import com.example.bitcoinservice.model.TransactionStatus;
import com.example.bitcoinservice.repo.SellerRepo;
import com.example.bitcoinservice.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/bitcoin-service")
public class BitcoinController {

    @Autowired
    private TransactionRepo transactionRepo;

    private String API_token = new String("szx5-PgTdEAggxxp1Fy9zxNZv6VFBWCBELsrtyF7");

    @Autowired
    HttpComponentsClientHttpRequestFactory requestFactory;

//    @Autowired
//    Monitor m;

    @Autowired
    private RestTemplate cudni_rest;



    @Autowired
    private SellerRepo sellerRepo;

//    @RequestMapping(value = "/start", method = RequestMethod.POST)
//    public ResponseEntity<JSONObject> test(@RequestBody String requestBody) throws Exception{
//       // CoingateReqDTO body = new CoingateReqDTO("1", 0.00001, "EUR", "EUR");
//        JSONObject body = new JSONObject();
//        body.put("order_id", "1");
//        body.put("price_amount", 0.11);
//        body.put("price_currency", "EUR");
//        body.put("receive_currency", "EUR");
//        HttpHeaders post_header = new HttpHeaders();
//        post_header.set("Authorization", "Token " + API_token);
//        HttpEntity<JSONObject> post_request = new HttpEntity<JSONObject>(body, post_header);
//        ResponseEntity<JSONObject> post_response = REST_template.postForEntity("https://api-sandbox.coingate.com/v2/orders", post_request, JSONObject.class);
//        JSONObject responseBody = new JSONObject();
//        responseBody = post_response.getBody();
//        int transaction_id = (int) responseBody.get("id");
//        String payment_url = (String) responseBody.get("payment_url");
//        System.out.println(payment_url);
//        return post_response;
//
//
//        // POSALJI KORISNIKA NA PAYMENT SAJT
//
//    }

    @RequestMapping(value = "/newSeller", method = RequestMethod.POST)
    public ResponseEntity<CustomerResponseDTO> newSeller(@RequestBody CustomerRequestDTO dto){
        Seller s = new Seller();
        s.setId(dto.getSellerId());
//        s.setSecret(dto.getSecret());

        sellerRepo.save(s);

        return new ResponseEntity<CustomerResponseDTO>(new CustomerResponseDTO("bitcoin-new-customer", s.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/newSellerData", method = RequestMethod.POST)
    public ResponseEntity<String> newSellerData(@RequestBody NewSellerDTO dto){
        Seller s = sellerRepo.findOneById(dto.getId());
        s.setSecret(dto.getSecret());

        sellerRepo.save(s);

        updateSeller(s);




        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public ResponseEntity<PaymentResponseDTO> transaction(@RequestBody TransactionRequestDTO request){
        CoingateCreateRequestDTO coingateCreateRequest = new CoingateCreateRequestDTO();

        RestTemplate REST_template = new RestTemplate(this.requestFactory);


//        System.out.println(request.amount);
        coingateCreateRequest.setPrice_amount(request.getAmount()* 0.00000118); // convert from EUR to BTC
        coingateCreateRequest.setPrice_currency("BTC");
        coingateCreateRequest.setReceive_currency("BTC");

        Seller seller = sellerRepo.findOneById(request.getSeller_id());

        HttpHeaders coingateRequestHeader = new HttpHeaders();
        coingateRequestHeader.set("Authorization", "Token " + seller.getSecret());

        HttpEntity<?> cgrequest = new HttpEntity<>(coingateCreateRequest, coingateRequestHeader);

        HttpEntity<CoingateCreateRequestDTO> coingateRequest = new HttpEntity<>(coingateCreateRequest, coingateRequestHeader);
//        System.out.println(coingateCreateRequest.getPrice_amount());
//        System.out.println(coingateCreateRequest.getPrice_currency());
//        System.out.println(coingateCreateRequest.getReceive_currency());
//        System.out.println(coingateRequest);

        ResponseEntity<CoingateCreateResponseDTO> coingateResponse = REST_template.postForEntity("https://api-sandbox.coingate.com/v2/orders", cgrequest, CoingateCreateResponseDTO.class);
//        System.out.println(coingateResponse);
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setBuyer_id(request.getBuyer_id());
        transaction.setSeller_id(request.getSeller_id());
        transaction.setBuyer_name(request.getBuyer_name());
        transaction.setSeller_name(request.getSeller_name());
        transaction.setId((Integer) coingateResponse.getBody().getId());
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        transactionRepo.save(transaction);

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setMerchantOrderId(request.getTransaction_id());
        response.setPaymentId(coingateResponse.getBody().getId());
        response.setPaymentUrl(coingateResponse.getBody().getPayment_url());

        CompletableFuture<Object> completableFuture = CompletableFuture.supplyAsync(() -> {

            Timer timer = new Timer();
            long pocetak = System.currentTimeMillis();

            //na svakih 10 sekundi proverava da li se promenio status transakcije
            //nakon 5min, transakcija automatski postaje neuspesna
            timer.schedule(new TimerTask(){

                @Override
                public void run(){

                    long trenutno = System.currentTimeMillis();

                    long vreme = trenutno - pocetak;

                    HttpHeaders post_header = new HttpHeaders();
                    post_header.set("Authorization", "Token " + seller.getSecret());
                    HttpEntity<?> get_request = new HttpEntity<>(post_header);
                    ResponseEntity<GetResponseDTO> get_response = REST_template.exchange("https://api-sandbox.coingate.com/v2/orders/" + coingateResponse.getBody().getId(), HttpMethod.GET, get_request, GetResponseDTO.class);

                    String status = get_response.getBody().getStatus();

                    System.out.println(status);

                    if(status.equals("paid")){
                        System.out.println("Sending paid response");
                        Transaction transaction = transactionRepo.findOneById(coingateResponse.getBody().getId());
                        transaction.setTransactionStatus(TransactionStatus.PAID);
                        transactionRepo.save(transaction);
                        StatusDTO statusDTO = new StatusDTO();
                        statusDTO.setStatus(status);
                        statusDTO.setMessage("Uspesno placeno");
                        cudni_rest.postForEntity("https://localhost:8096/KP/paid/" + request.getRad_id() + "/" + request.getSeller_id() + "/" + request.getBuyer_id(), statusDTO, String.class);
                        timer.cancel();
                    }

                    else if(status.equals("invalid")){
                        System.out.println("Sending invalid response");

                        Transaction transaction = transactionRepo.findOneById(coingateResponse.getBody().getId());
                        transaction.setTransactionStatus(TransactionStatus.INVALID);
                        transactionRepo.save(transaction);
                        StatusDTO statusDTO = new StatusDTO();
                        statusDTO.setStatus(status);
                        statusDTO.setMessage("Neuspesna transakcija");
                        cudni_rest.postForEntity("https://localhost:8096/KP/paid/" + request.getRad_id() + "/" + request.getSeller_id() + "/" + request.getBuyer_id(), statusDTO, String.class);
                        timer.cancel();
                    }

                    else if(status.equals("expired")){
                        System.out.println("Sending expired response");

                        Transaction transaction = transactionRepo.findOneById(coingateResponse.getBody().getId());
                        transaction.setTransactionStatus(TransactionStatus.EXPIRED);
                        transactionRepo.save(transaction);
                        StatusDTO statusDTO = new StatusDTO();
                        statusDTO.setStatus(status);
                        statusDTO.setMessage("Isteklo vreme");
                        cudni_rest.postForEntity("https://localhost:8096/KP/paid/" + request.getRad_id() + "/" + request.getSeller_id() + "/" + request.getBuyer_id(), statusDTO, String.class);
                        timer.cancel();
                    }

                    else if(status.equals("canceled")){
                        System.out.println("Sending cancelled response");

                        Transaction transaction = transactionRepo.findOneById(coingateResponse.getBody().getId());
                        System.out.println(transaction);
                        transaction.setTransactionStatus(TransactionStatus.CANCELLED);
                        transactionRepo.save(transaction);
                        StatusDTO statusDTO = new StatusDTO();
                        statusDTO.setStatus(status);
                        statusDTO.setMessage("Otkazali ste transakciju");
                        cudni_rest.postForEntity("https://localhost:8096/KP/paid/" + request.getRad_id() + "/" + request.getSeller_id() + "/" + request.getBuyer_id(), statusDTO, String.class);
                        timer.cancel();
                    }

                    if(vreme > 8640000){
                        Transaction transaction = transactionRepo.findOneById(coingateResponse.getBody().getId());
                        transaction.setTransactionStatus(TransactionStatus.EXPIRED);
                        transactionRepo.save(transaction);
                        StatusDTO statusDTO = new StatusDTO();
                        statusDTO.setStatus(status);
                        statusDTO.setMessage("Nesto nije dobro");
                        cudni_rest.postForEntity("https://localhost:8096/KP/paid/" + request.getRad_id() + "/" + request.getSeller_id() + "/" + request.getBuyer_id(), statusDTO, String.class);
                        timer.cancel();
                    }

                }
            },0,10000);

            return "end";
        });

//        try {
//            Thread.sleep(5000);
//
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        Monitor m = new Monitor(coingateResponse.getBody().getId(), seller.getSecret(), request.getRad_id(), request.getSeller_id(), request.getBuyer_id());

//        Thread t = new Thread(m);
//        t.start();

//        Nesto n = new Nesto(coingateResponse.getBody().getId(), seller.getSecret(), request.getRad_id(), request.getSeller_id(), request.getBuyer_id());
//        Thread t = new Thread(n);
//        t.start();
//        m.mon(coingateResponse.getBody().getId(), seller.getSecret(), request.getRad_id(), request.getSeller_id(), request.getBuyer_id());

        return new ResponseEntity<PaymentResponseDTO>( response, HttpStatus.OK);

    }

//    @RequestMapping(value = "/monitor", method = RequestMethod.POST)
//    public ResponseEntity<JSONObject> monitorTransaction(@RequestBody MonitorCoingateDTO dto) throws Exception{
//
//        while(true) {
//            JSONObject get_body = new JSONObject();
//            int id = dto.getId();
//            get_body.put("id", id);
//            HttpHeaders post_header = new HttpHeaders();
//            post_header.set("Authorization", "Token " + dto.getSecret());
//            HttpEntity<JSONObject> get_request = new HttpEntity<>(get_body, post_header);
//            ResponseEntity<JSONObject> get_response = REST_template.exchange("https://api-sandbox.coingate.com/v2/orders/" + id, HttpMethod.GET, get_request, JSONObject.class);
//            String status = new String((String) get_response.getBody().get("status"));
//
//            if (status.equals("paid")){
//                // PRINT INFO SUCCESS
//                Transaction transaction = transactionRepo.findOneById(id);
//                transaction.setTransactionStatus(TransactionStatus.PAID);
//                transactionRepo.save(transaction);
//                return get_response;
//            }
//
//            else if(status.equals("invalid")){
//                // PRINT INFO INVALID EXPIRED OR CANCELED
//                Transaction transaction = transactionRepo.findOneById(id);
//                transaction.setTransactionStatus(TransactionStatus.INVALID);
//                transactionRepo.save(transaction);
//                return get_response;
//            }
//
//            else if(status.equals("expired")){
//                Transaction transaction = transactionRepo.findOneById(id);
//                transaction.setTransactionStatus(TransactionStatus.EXPIRED);
//                transactionRepo.save(transaction);
//                return get_response;
//            }
//
//            else if(status.equals("canceled")){
//                Transaction transaction = transactionRepo.findOneById(id);
//                transaction.setTransactionStatus(TransactionStatus.CANCELLED);
//                transactionRepo.save(transaction);
//                return get_response;
//            }
//
//            else {
//                System.out.println(status);
//                Thread.sleep(5000);
//            }
//        }
//
//    }

    private void updateSeller(Seller customer) {
        RestTemplate REST_template = new RestTemplate(this.requestFactory);
        HttpEntity<UpdateSellerDTO> entity = new HttpEntity<UpdateSellerDTO>(new UpdateSellerDTO(customer.getId(), "bitcoin"));
        ResponseEntity<String> responseEntity = REST_template.exchange("https://localhost:8091/sellers/updateSeller",
                HttpMethod.PUT, entity, String.class);
    }
}
