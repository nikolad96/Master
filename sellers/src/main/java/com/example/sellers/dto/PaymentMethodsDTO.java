package com.example.sellers.dto;

import com.example.sellers.model.PaymentMethods;

import java.util.List;

public class PaymentMethodsDTO {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<PaymentMethods> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethods> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    private List<PaymentMethods> paymentMethods;
}
