package com.example.naucnacentrala.dto;

import com.example.naucnacentrala.model.PaymentMethod;

import java.util.List;

public class PaymentMethodsDTO {
    private Integer id;

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }
    List<PaymentMethod> paymentMethods;

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
