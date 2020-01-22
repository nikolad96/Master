package com.example.paymentcardcenter.dto;

import com.example.paymentcardcenter.model.TransactionState;

public class TransactionStateDTO {

    private TransactionState transactionState;

    public TransactionStateDTO(){
    }

    public TransactionStateDTO(TransactionState transactionState) {
        this.transactionState = transactionState;
    }

    public TransactionState getTransactionState() {
        return transactionState;
    }

    public void setTransactionState(TransactionState transactionState) {
        this.transactionState = transactionState;
    }
}
