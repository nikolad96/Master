package com.example.banka.model;

import com.example.banka.dto.TransactionState;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private double amount;

    @Column
    private Date timestamp;

    @Column
    private TransactionState state;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Customer customer;

    public Transaction() {
    }

    public Transaction(double amount, Date timestamp, TransactionState state) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionState getState() { return state; }

    public void setState(TransactionState state) { this.state = state; }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
