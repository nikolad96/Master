package com.example.banka.dto;

import com.example.banka.model.TransactionState;

import java.util.Date;

public class AcquirerResponseDTO {

    private Integer acquirerOrderId;
    private Date acquirerTimestamp;
    private TransactionState state;
    private String message;

    public AcquirerResponseDTO() {
    }

    public AcquirerResponseDTO(Integer acquirerOrderId, Date acquirerTimestamp, TransactionState state, String message) {
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.state = state;
        this.message = message;
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

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
