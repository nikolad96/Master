package com.example.banka.dto;

import com.example.banka.model.TransactionState;

import java.util.Date;

public class IssuerResponseDTO {

    private TransactionState state;
    private Integer acquirerOrderId;
    private Date acquirerTimestamp;
    private Integer issuerOrderId;
    private Date issuerTimestamp;

    public IssuerResponseDTO() {
    }

    public IssuerResponseDTO(TransactionState state, Integer acquirerOrderId, Date acquirerTimestamp, Integer issuerOrderId, Date issuerTimestamp) {
        this.state = state;
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.issuerOrderId = issuerOrderId;
        this.issuerTimestamp = issuerTimestamp;
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
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

    public Integer getIssuerOrderId() {
        return issuerOrderId;
    }

    public void setIssuerOrderId(Integer issuerOrderId) {
        this.issuerOrderId = issuerOrderId;
    }

    public Date getIssuerTimestamp() {
        return issuerTimestamp;
    }

    public void setIssuerTimestamp(Date issuerTimestamp) {
        this.issuerTimestamp = issuerTimestamp;
    }
}
