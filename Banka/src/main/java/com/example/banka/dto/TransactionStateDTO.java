package com.example.banka.dto;

import com.example.banka.model.TransactionState;

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
