package com.example.banka.dto;

public class PaymentResponseDTO {

    private String paymentUrl;
    private Integer paymentId;

    public PaymentResponseDTO() {
    }

    public PaymentResponseDTO(String paymentUrl, Integer paymentId) {
        this.paymentUrl = paymentUrl;
        this.paymentId = paymentId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }
}
