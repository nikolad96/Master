package com.example.naucnacentrala.dto;

public class NewSellerDTO {

    private Integer sellerId;
    private Integer paymentMethodId;
    private String name;

    public NewSellerDTO() {
    }

    public NewSellerDTO(Integer sellerId, Integer paymentMethodId, String name) {
        this.sellerId = sellerId;
        this.paymentMethodId = paymentMethodId;
        this.name = name;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
