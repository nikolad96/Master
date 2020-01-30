package com.example.sellers.dto;

import com.example.sellers.model.PaymentMethod;

import java.util.List;

public class SellerDTO {

    private Integer id;
    private String name;
    private String secret;
    private List<PaymentMethod> paymentMethods;

    public SellerDTO() {
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
