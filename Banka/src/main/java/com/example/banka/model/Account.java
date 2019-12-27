package com.example.banka.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String pan;

    @Column
    private String securityCode;

    @Column
    private String cardholderName;

    @Column
    private String expirationDate;

    @Column
    private double balance;

    @Column
    private double reserved;

    @OneToOne(fetch = FetchType.LAZY)
    private Customer customer;

    public Account() {
    }

    public Account(String pan, String securityCode, String cardholderName, String expirationDate, double balance, double reserved) {
        this.pan = pan;
        this.securityCode = securityCode;
        this.cardholderName = cardholderName;
        this.expirationDate = expirationDate;
        this.balance = balance;
        this.reserved = reserved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getReserved() {
        return reserved;
    }

    public void setReserved(double reserved) {
        this.reserved = reserved;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
