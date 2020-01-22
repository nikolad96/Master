package com.example.bankaservice.dto;

import com.example.bankaservice.model.TransactionState;

public class CardResponseDTO {

    private String url;
    private TransactionState state;
    private Integer paymentId;

    public CardResponseDTO() {
    }

    public CardResponseDTO(String url, TransactionState state) {
        this.url = url;
        this.state = state;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) {
        this.url = url;
    }

    public TransactionState getState() { return state; }

    public void setState(TransactionState state) { this.state = state; }

    public Integer getPaymentId() { return paymentId; }

    public void setPaymentId(Integer paymentId) { this.paymentId = paymentId; }
}
