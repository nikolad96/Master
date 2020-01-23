package com.example.loadbalancer;

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
public class LoadBalancerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoadBalancerApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registrationBean =
                new FilterRegistrationBean(new CORSFilter());
        registrationBean.setName("CORS Filter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
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
