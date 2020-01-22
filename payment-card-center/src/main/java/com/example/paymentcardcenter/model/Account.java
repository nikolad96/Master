package com.example.paymentcardcenter.model;

import javax.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_id")
    private Integer customerId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Bank bank;

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() { return customerId; }

    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public Bank getBank() { return bank; }

    public void setBank(Bank bank) { this.bank = bank; }
}
