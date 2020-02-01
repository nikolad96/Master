package com.example.paypalservice.model;

import javax.persistence.*;

@Entity
public class PaypalMerchant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer sellerId;

    @Column
    private String clientId;

    @Column
    private String clientSecret;

    public PaypalMerchant(Integer id, Integer sellerId, String clientId, String clientSecret) {
        this.id = id;
        this.sellerId = sellerId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public PaypalMerchant() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
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

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
