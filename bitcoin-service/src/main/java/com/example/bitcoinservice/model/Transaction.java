package com.example.bitcoinservice.model;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer sellerId;

    @Column
    private Integer buyerId;

    @Column
    private String seller_name;

    @Column
    private String buyer_name;

    @Column
    private Double amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeller_id() {
        return sellerId;
    }

    public void setSeller_id(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getBuyer_id() {
        return buyerId;
    }

    public void setBuyer_id(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

}
