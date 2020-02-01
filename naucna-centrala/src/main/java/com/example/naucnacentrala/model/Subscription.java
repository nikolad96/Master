package com.example.naucnacentrala.model;

import javax.persistence.*;


@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer sellerId;

    @Column
    private String startDate;

    @Column
    private String username;


    @Column
    private String endDate;

    @Column
    private String state;

    @Column
    private String description;



    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Casopis casopis;

    public Casopis getCasopis() {
        return casopis;
    }

    public void setCasopis(Casopis casopis) {
        this.casopis = casopis;
    }

    public Subscription() {
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

    public Subscription(Integer id, Integer sellerId, String startDate, String username, String endDate, String state, String description) {
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
