package com.example.bankaservice.dto;

import java.util.Date;

public class PaymentRequestDTO {

    private String merchantId;
    private String merchantPassword;
    private double amount;
    private Integer merchantOrderId;
    private Date merchantTimestamp;

    public PaymentRequestDTO() {
    }

    public PaymentRequestDTO(String merchantId, String merchantPassword, double amount, Integer merchantOrderId, Date merchantTimestamp) {
        this.merchantId = merchantId;
        this.merchantPassword = merchantPassword;
        this.amount = amount;
        this.merchantOrderId = merchantOrderId;
        this.merchantTimestamp = merchantTimestamp;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Integer merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Date getMerchantTimestamp() {
        return merchantTimestamp;
    }

    public void setMerchantTimestamp(Date merchantTimestamp) {
        this.merchantTimestamp = merchantTimestamp;
    }
}
