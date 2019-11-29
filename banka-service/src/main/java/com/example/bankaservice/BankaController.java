package com.example.bankaservice;


import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;





import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.util.Collections;

@RestController
@RequestMapping(value = "/banka-test")
public class BankaController {

    @Autowired
    RestTemplate REST_template;

    public RestTemplate restTemplate() throws Exception {
        String password = "123456";
        String resourcePath = "keystore/keystore.p12";
        String path = "src/main/resources/keystore/keystore.p12";
        Resource resource = new ClassPathResource(resourcePath);

        File file = resource.getFile();

        FileInputStream is = new FileInputStream(path);
        KeyStore trustStore =  KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(is, password.toCharArray());
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial( file, password.toCharArray())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> test(@RequestBody String requestBody) {
        HttpEntity<String> HReq=new HttpEntity<String>(requestBody);
        System.out.println("usao u banka service");
        //salje request ka banci
//        ResponseEntity<String> response = REST_template.postForEntity("https://localhost:8082/bank", HReq, String.class);
//        return response;

        try{
            RestTemplate r = restTemplate();
            return r.postForEntity("https://localhost:8082/bank", HReq, String.class);
        }catch(Exception e) {
            return REST_template.postForEntity("https://localhost:8082/bank", HReq, String.class);
        }
    }
}
