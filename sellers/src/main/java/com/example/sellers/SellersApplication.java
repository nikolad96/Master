package com.example.sellers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class SellersApplication {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(SellersApplication.class, args);
    }

//    @Configuration
//    public class SSLConfig {
//
//        @Autowired
//        private Environment env;
//
//        @PostConstruct
//        private void configureSSL() {
//            Properties systemProps = System.getProperties();
//            systemProps.put("javax.net.ssl.trustStore", env.getProperty("trust.store"));
//            systemProps.put("javax.net.ssl.trustStorePassword",env.getProperty("trust.store.password"));
//            System.setProperties(systemProps);
//        }
//    }

}
