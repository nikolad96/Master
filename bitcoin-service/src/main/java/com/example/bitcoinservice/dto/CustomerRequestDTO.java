package com.example.bitcoinservice.dto;

public class CustomerRequestDTO {
    private Integer sellerId;
    private String name;

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
