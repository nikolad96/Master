package com.example.zuulservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Properties;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ZuulServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServiceApplication.class, args);

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
