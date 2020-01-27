package com.example.sellers.model;

import javax.persistence.*;
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

    @Column
    private String pib;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PaymentMethod> paymentMethods;

    public Seller() {

    }

    public Seller(Integer sellerId, String name, String pib) {
        this.sellerId = sellerId;
        this.name = name;
        this.pib = pib;
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

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
