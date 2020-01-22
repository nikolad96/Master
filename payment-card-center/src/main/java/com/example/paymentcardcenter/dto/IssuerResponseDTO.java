package com.example.paymentcardcenter.dto;

import com.example.paymentcardcenter.model.TransactionState;

public class IssuerResponseDTO {

    private TransactionState state;
    private Integer acquirerOrderId;
    private String acquirerTimestamp;
    private Integer issuerOrderId;
    private String issuerTimestamp;

    public IssuerResponseDTO() {
    }

    public IssuerResponseDTO(TransactionState state, Integer acquirerOrderId, String acquirerTimestamp, Integer issuerOrderId, String issuerTimestamp) {
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

    public String getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(String acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public Integer getIssuerOrderId() {
        return issuerOrderId;
    }

    public void setIssuerOrderId(Integer issuerOrderId) {
        this.issuerOrderId = issuerOrderId;
    }

    public String getIssuerTimestamp() {
        return issuerTimestamp;
    }

    public void setIssuerTimestamp(String issuerTimestamp) {
        this.issuerTimestamp = issuerTimestamp;
    }
}
