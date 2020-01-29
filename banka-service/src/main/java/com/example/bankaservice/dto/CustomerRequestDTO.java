package com.example.bankaservice.dto;

public class CustomerRequestDTO {

    private Integer sellerId;
    private String name;

    public CustomerRequestDTO() {
    }

    public CustomerRequestDTO(Integer sellerId, String name) {
        this.sellerId = sellerId;
        this.name = name;
    }

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
