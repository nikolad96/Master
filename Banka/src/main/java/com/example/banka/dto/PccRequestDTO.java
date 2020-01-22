package com.example.banka.dto;

import com.example.banka.model.TransactionState;

import java.util.Date;

public class PccRequestDTO {

    private Integer acquirerOrderId;
    private Date acquirerTimestamp;
    private double amount;
    private TransactionState state;
    private CardRequestDTO cardRequestDTO;

    public PccRequestDTO() {
    }

    public PccRequestDTO(Integer acquirerOrderId, Date acquirerTimestamp, double amount, TransactionState state, CardRequestDTO cardRequestDTO) {
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.amount = amount;
        this.state = state;
        this.cardRequestDTO = cardRequestDTO;
    }

    public Integer getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(Integer acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public Date getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(Date acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public CardRequestDTO getCardRequestDTO() {
        return cardRequestDTO;
    }

    public void setCardRequestDTO(CardRequestDTO cardRequestDTO) {
        this.cardRequestDTO = cardRequestDTO;
    }
}
