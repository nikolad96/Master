package com.example.paypalservice;

public class PayPalDTO {
    private String payerId;
    private String paymentId;

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PayPalDTO() {

    }
}
