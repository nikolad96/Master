package com.example.nc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/dummyNC")
public class NCDummyController {

    @Autowired
    public RestTemplate REST_template;

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public ResponseEntity<String> test(){
        String requestBody = "Trazim placanje";

        HttpEntity<String> HttpRequest = new HttpEntity<String>(requestBody);

        return REST_template.postForEntity("http://localhost:8081/KP", HttpRequest, String.class);
    }
}
