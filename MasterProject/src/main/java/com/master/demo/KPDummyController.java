package com.master.demo;

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
@RequestMapping(value="/KP")
public class KPDummyController {
    @Autowired
    RestTemplate REST_template;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> test(@RequestBody String requestBody){

        HttpEntity<String> HTTP_request = new HttpEntity<String>(requestBody);
        String responseBody = "Placeno";
        return new ResponseEntity<String>(responseBody, HttpStatus.OK);
//        return REST_template.postForEntity("http://localhost:8082/bank", HTTP_request, String.class);
    }
}
