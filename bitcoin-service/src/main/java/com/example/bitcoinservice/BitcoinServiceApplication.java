package com.example.bitcoinservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Properties;

//@EnableEurekaClient
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BitcoinServiceApplication {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(BitcoinServiceApplication.class, args);
    }

    @Configuration
    public class SSLConfig {

        @Autowired
        private Environment env;

        @PostConstruct
        private void configureSSL() {
            Properties systemProps = System.getProperties();
            systemProps.put("javax.net.ssl.trustStore", env.getProperty("trust.store"));
            systemProps.put("javax.net.ssl.trustStorePassword",env.getProperty("trust.store.password"));
            System.setProperties(systemProps);
        }
    }

}
