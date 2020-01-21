package com.example.bitcoinservice.controller;


import com.example.bitcoinservice.dto.*;
import com.example.bitcoinservice.model.Seller;
import com.example.bitcoinservice.model.Transaction;
import com.example.bitcoinservice.model.TransactionStatus;
import com.example.bitcoinservice.repo.SellerRepo;
import com.example.bitcoinservice.repo.TransactionRepo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/bitcoin-service")
public class BitcoinController {

    @Autowired
    private TransactionRepo transactionRepo;

    private String API_token = new String("szx5-PgTdEAggxxp1Fy9zxNZv6VFBWCBELsrtyF7");

    @Autowired
    RestTemplate REST_template;

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
    public ResponseEntity<String> newSeller(@RequestBody NewSellerDTO dto){
        Seller s = new Seller();
        s.setId(dto.getId());
        s.setSecret(dto.getSecret());

        sellerRepo.save(s);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public ResponseEntity<CoingateCreateResponseDTO> transaction(@RequestBody TransactionRequestDTO request){
        CoingateCreateRequestDTO coingateCreateRequest = new CoingateCreateRequestDTO();



//        System.out.println(request.amount);
        coingateCreateRequest.setPrice_amount(request.getAmount());
        coingateCreateRequest.setPrice_currency("BTC");
        coingateCreateRequest.setReceive_currency("BTC");

        Seller seller = sellerRepo.findOneById(request.getSeller_id());

        HttpHeaders coingateRequestHeader = new HttpHeaders();
        coingateRequestHeader.set("Authorization", "Token " + seller.getSecret());
        HttpEntity<CoingateCreateRequestDTO> coingateRequest = new HttpEntity<>(coingateCreateRequest, coingateRequestHeader);
        ResponseEntity<CoingateCreateResponseDTO> coingateResponse = REST_template.postForEntity("https://api-sandbox.coingate.com/v2/orders", coingateRequest, CoingateCreateResponseDTO.class);
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setBuyer_id(request.getBuyer_id());
        transaction.setSeller_id(request.getSeller_id());
        transaction.setBuyer_name(request.getBuyer_name());
        transaction.setSeller_name(request.getSeller_name());
        transaction.setId((Integer) coingateResponse.getBody().getId());
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        transactionRepo.save(transaction);
        return coingateResponse;

    }

    @RequestMapping(value = "/monitor", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> monitorTransaction(@RequestBody MonitorCoingateDTO dto) throws Exception{

        while(true) {
            JSONObject get_body = new JSONObject();
            int id = dto.getId();
            get_body.put("id", id);
            HttpHeaders post_header = new HttpHeaders();
            post_header.set("Authorization", "Token " + dto.getSecret());
            HttpEntity<JSONObject> get_request = new HttpEntity<>(get_body, post_header);
            ResponseEntity<JSONObject> get_response = REST_template.exchange("https://api-sandbox.coingate.com/v2/orders/" + id, HttpMethod.GET, get_request, JSONObject.class);
            String status = new String((String) get_response.getBody().get("status"));

            if (status.equals("paid")){
                // PRINT INFO SUCCESS
                Transaction transaction = transactionRepo.findOneById(id);
                transaction.setTransactionStatus(TransactionStatus.PAID);
                transactionRepo.save(transaction);
                return get_response;
            }

            else if(status.equals("invalid")){
                // PRINT INFO INVALID EXPIRED OR CANCELED
                Transaction transaction = transactionRepo.findOneById(id);
                transaction.setTransactionStatus(TransactionStatus.INVALID);
                transactionRepo.save(transaction);
                return get_response;
            }

            else if(status.equals("expired")){
                Transaction transaction = transactionRepo.findOneById(id);
                transaction.setTransactionStatus(TransactionStatus.EXPIRED);
                transactionRepo.save(transaction);
                return get_response;
            }

            else if(status.equals("canceled")){
                Transaction transaction = transactionRepo.findOneById(id);
                transaction.setTransactionStatus(TransactionStatus.CANCELLED);
                transactionRepo.save(transaction);
                return get_response;
            }

            else {
                System.out.println(status);
                Thread.sleep(5000);
            }
        }

    }
}
