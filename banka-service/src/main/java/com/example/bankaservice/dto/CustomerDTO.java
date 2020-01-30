package com.example.bankaservice.dto;

public class CustomerDTO {

    private Integer customerId;
    private String merchantId;
    private String merchantPassword;

    public CustomerDTO() {
    }

    public CustomerDTO(String merchantId, String merchantPassword) {
        this.merchantId = merchantId;
        this.merchantPassword = merchantPassword;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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
}
