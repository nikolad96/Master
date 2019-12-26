package com.example.bitcoinservice;


import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/bitcoin-service")
public class BitcoinController {

    @Autowired
    RestTemplate REST_template;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> test(@RequestBody String requestBody) throws Exception{
       // CoingateReqDTO body = new CoingateReqDTO("1", 0.00001, "EUR", "EUR");
        JSONObject body = new JSONObject();
        body.put("order_id", "1");
        body.put("price_amount", 0.11);
        body.put("price_currency", "EUR");
        body.put("receive_currency", "EUR");
        HttpHeaders post_header = new HttpHeaders();
        String API_token = new String("szx5-PgTdEAggxxp1Fy9zxNZv6VFBWCBELsrtyF7");
        post_header.set("Authorization", "Token " + API_token);
        HttpEntity<JSONObject> post_request = new HttpEntity<JSONObject>(body, post_header);
        ResponseEntity<JSONObject> post_response = REST_template.postForEntity("https://api-sandbox.coingate.com/v2/orders", post_request, JSONObject.class);
        JSONObject responseBody = new JSONObject();
        responseBody = post_response.getBody();
        int transaction_id = (int) responseBody.get("id");
        String payment_url = (String) responseBody.get("payment_url");
        System.out.println(payment_url);

        // POSALJI KORISNIKA NA PAYMENT SAJT

        ResponseEntity<JSONObject> get_response;

        while(true) {
            JSONObject get_body = new JSONObject();
            get_body.put("id", transaction_id);
            HttpEntity<JSONObject> get_request = new HttpEntity<>(get_body, post_header);
            get_response = REST_template.exchange("https://api-sandbox.coingate.com/v2/orders/" + transaction_id, HttpMethod.GET, get_request, JSONObject.class);
            String status = new String((String) get_response.getBody().get("status"));

            if (status.equals("paid")){
                // PRINT INFO SUCCESS
                break;
            }

            else if(status.equals("invalid") || status.equals("expired") || status.equals("canceled")){
                // PRINT INFO INVALID EXPIRED OR CANCELED
                break;
            }

            else {
                System.out.println(status);
                Thread.sleep(5000);
            }
        }

        
        return get_response;
    }

    public monitorTransaction()
}
