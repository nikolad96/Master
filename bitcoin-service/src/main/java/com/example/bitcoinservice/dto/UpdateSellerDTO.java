package com.example.bitcoinservice.dto;

public class UpdateSellerDTO {
    private Integer sellerId;
    private String paymentMethodName;

    public UpdateSellerDTO(Integer sellerId, String paymentMethodName) {
        this.sellerId = sellerId;
        this.paymentMethodName = paymentMethodName;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
}
