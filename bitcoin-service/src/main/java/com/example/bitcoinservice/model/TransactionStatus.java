package com.example.bitcoinservice.model;

import javax.persistence.*;

public enum TransactionStatus {
    PAID,
    EXPIRED,
    CANCELLED,
    INVALID,
    PENDING
}