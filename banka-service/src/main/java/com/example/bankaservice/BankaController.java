package com.example.bankaservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/banka-test")
public class BankaController {

    @Autowired
    RestTemplate REST_template;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> test(@RequestBody String requestBody) {
        HttpEntity<String> HReq=new HttpEntity<String>(requestBody);
        System.out.println("usao u banka service");
        //salje request ka banci
        ResponseEntity<String> response = REST_template.postForEntity("http://localhost:8082/bank", HReq, String.class);
        return response;
    }
}
