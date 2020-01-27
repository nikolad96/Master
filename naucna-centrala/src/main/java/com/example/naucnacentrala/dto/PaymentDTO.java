package com.example.naucnacentrala.dto;

public class PaymentDTO {

    private Integer sellerId;
    private double amount;

    public PaymentDTO() {
    }

    public PaymentDTO(Integer sellerId, double amount) {
        this.sellerId = sellerId;
        this.amount = amount;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
