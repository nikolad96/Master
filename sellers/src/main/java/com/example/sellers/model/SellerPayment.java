package com.example.sellers.model;

import javax.persistence.*;

@Entity
public class SellerPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PaymentMethod paymentMethod;

    @Column
    private boolean potvrdjeno;

    public SellerPayment() {
    }

    public SellerPayment(PaymentMethod paymentMethod, boolean potvrdjeno) {
        this.paymentMethod = paymentMethod;
        this.potvrdjeno = potvrdjeno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPotvrdjeno() {
        return potvrdjeno;
    }

    public void setPotvrdjeno(boolean potvrdjeno) {
        this.potvrdjeno = potvrdjeno;
    }
}
