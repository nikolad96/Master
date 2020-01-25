package com.example.naucnacentrala.dto;

import com.example.naucnacentrala.model.PaymentMethod;

import java.util.List;

public class PaymentMethodsDTO {

    private Integer id;
    List<PaymentMethod> paymentMethods;

    public PaymentMethodsDTO() {
    }

    public PaymentMethodsDTO(Integer id, List<PaymentMethod> paymentMethods) {
        this.id = id;
        this.paymentMethods = paymentMethods;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
