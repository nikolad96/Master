package com.example.paypalservice.dto;

import javax.persistence.Column;

public class SubscriptionDTO {
    private Integer id;


    private Integer sellerId;


    private String startDate;


    private String username;


    private String endDate;

    private String state;

    private String description;


    public SubscriptionDTO() {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SubscriptionDTO(Integer id, Integer sellerId, String startDate, String username, String endDate, String state, String description) {
        this.id = id;
        this.sellerId = sellerId;
        this.startDate = startDate;
        this.username = username;
        this.endDate = endDate;
        this.state = state;
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
