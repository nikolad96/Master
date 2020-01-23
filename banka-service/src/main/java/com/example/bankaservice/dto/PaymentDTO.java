package com.example.bankaservice.dto;

public class PaymentDTO {

    private Integer id;
    private double amount;

    public PaymentDTO() {
    }

    public PaymentDTO(Integer id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
