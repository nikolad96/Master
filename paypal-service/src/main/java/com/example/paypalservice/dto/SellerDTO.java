package com.example.paypalservice.dto;

public class SellerDTO {
    private Integer id;
    private String clientId;
    private String clientSecret;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String cleintSecret) {
        this.clientSecret = cleintSecret;
    }

    public SellerDTO() {
    }
}
