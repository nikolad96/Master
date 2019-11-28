package com.example.banka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/bank")
public class DummyBankController {

    @Autowired
    RestTemplate REST_template;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> test(@RequestBody String requestBody) {
        String responseBody = "Placeno";
        System.out.println("usao u banku");
        return new ResponseEntity<String>(responseBody, HttpStatus.OK);
    }

}
