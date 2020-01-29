package com.example.sellers.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer sellerId;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SellerPayment> paymentMethods = new ArrayList<>();

    public Seller() {

    }

    public Seller(Integer sellerId, String name) {
        this.sellerId = sellerId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSellerId() { return sellerId; }

    public void setSellerId(Integer sellerId) { this.sellerId = sellerId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SellerPayment> getPaymentMethods() { return paymentMethods; }

    public void setPaymentMethods(List<SellerPayment> paymentMethods) { this.paymentMethods = paymentMethods; }
}
