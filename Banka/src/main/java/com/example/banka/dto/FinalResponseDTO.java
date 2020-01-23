package com.example.banka.dto;

import com.example.banka.model.TransactionState;

import java.util.Date;

public class FinalResponseDTO {

    private Integer merchantOrderId;
    private Integer acquirerOrderId;
    private Date acquirerTimestamp;
    private Integer paymentId;
    private TransactionState state;

    public FinalResponseDTO() {
    }

    public FinalResponseDTO(Integer merchantOrderId, Integer acquirerOrderId, Date acquirerTimestamp, Integer paymentId, TransactionState state) {
        this.merchantOrderId = merchantOrderId;
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.paymentId = paymentId;
        this.state = state;
    }

    public Integer getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Integer merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
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

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }
}
