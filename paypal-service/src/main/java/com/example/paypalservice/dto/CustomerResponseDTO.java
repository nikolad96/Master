package com.example.paypalservice.dto;

public class CustomerResponseDTO {
    private String redirectionUrl;
    private Integer customerId;

    public CustomerResponseDTO(String redirectionUrl, Integer customerId) {
        this.redirectionUrl = redirectionUrl;
        this.customerId = customerId;
    }

    public String getRedirectionUrl() {
        return redirectionUrl;
    }

    public void setRedirectionUrl(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
